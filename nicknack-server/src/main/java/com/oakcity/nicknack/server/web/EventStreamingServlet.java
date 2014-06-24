package com.oakcity.nicknack.server.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.context.support.HttpRequestHandlerServlet;

import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator.Feature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oakcity.nicknack.core.events.Event;
import com.oakcity.nicknack.core.providers.ProviderService;

@WebServlet(value="/api/eventsStream", name="eventsStream")
@Component("eventsStream")
public class EventStreamingServlet extends HttpRequestHandlerServlet implements HttpRequestHandler {

	@Autowired
	private ProviderService providerService;

	private volatile boolean isDestroyed = false;

	/**
	 * 
	 */
	private static final long serialVersionUID = 8528450620855423081L;

	@Override
	public void handleRequest(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {
		response.setHeader("Content-Type", "text/event-stream;charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache, no-store, max-age=0, must-revalidate");
		response.setHeader("Pragma", "no-cache");

		final Observable<Event> events = providerService.getEvents();

		final ObjectMapper jsonMapper = new ObjectMapper();
		jsonMapper.getFactory().disable(Feature.AUTO_CLOSE_TARGET);
		
		final AtomicBoolean ioError = new AtomicBoolean(false);

		final Subscription subscription = events.subscribe(new Action1<Event>() {

			@Override
			public void call(Event event) {
				try {
					final PrintWriter writer = response.getWriter();
					writer.write("data: ");
					jsonMapper.writeValue(writer, event);
					writer.write("\n\n");
					if (writer.checkError()) {
						ioError.set(true);
					}
				} catch (JsonGenerationException e) {
					// Log
				} catch (JsonMappingException e) {
					// Log	
				} catch (IOException e) {
					ioError.set(true);
					// Log
				}
			}
		});

		while (!isDestroyed && !ioError.get()) {
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// Don't care.
			}
		}
		
		subscription.unsubscribe();
		
	}
	
	
	
	@Override
	public void destroy() {
		this.isDestroyed = true;
		super.destroy();
	}

}
