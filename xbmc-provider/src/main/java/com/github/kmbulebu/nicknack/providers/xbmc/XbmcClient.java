package com.github.kmbulebu.nicknack.providers.xbmc;

import java.io.IOException;
import java.net.URI;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.eclipse.jetty.websocket.client.ClientUpgradeRequest;
import org.eclipse.jetty.websocket.client.WebSocketClient;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.kmbulebu.nicknack.providers.xbmc.json.JsonRpc;

@WebSocket
public class XbmcClient {
	
	private static final Logger logger = LogManager.getLogger(XbmcProvider.LOGGER_NAME);

	// Used for sending messages.
	private Session session;
	private final ObjectMapper mapper = new ObjectMapper();
	private final WebSocketClient client = new WebSocketClient();
	private URI websocketUri = null;
	
	private boolean stopRequested = false;
	
	private OnMessageReceivedListener listener;
	
	private static final long RETRY_SLOT_INTERVAL = 1000;

	@OnWebSocketClose
	public void onClose(int statusCode, String reason) throws Exception {
		if (logger.isTraceEnabled()) {
			logger.entry(statusCode, reason);
		}
		this.session = null;
		if (!stopRequested && websocketUri != null) {
			connect(websocketUri);
		}
		if (logger.isTraceEnabled()) {
			logger.exit();
		}
	}

	@OnWebSocketConnect
	public void onConnect(Session session) {
		if (logger.isTraceEnabled()) {
			logger.entry(session);
		}
		this.session = session;
		if (logger.isTraceEnabled()) {
			logger.exit();
		}
	}
	
	@OnWebSocketMessage
    public void onMessage(String msg) throws IOException {
		if (logger.isTraceEnabled()) {
			logger.entry(msg);
		}
		if (msg == null || msg.isEmpty()) {
			if (logger.isTraceEnabled()) {
				logger.exit();
			}
			return;
		}
		if (logger.isDebugEnabled()) {
			logger.debug("Received message from " + websocketUri + ". Message: " + msg);
		}
		JsonRpc message;
		try {
			message = mapper.readValue(msg, JsonRpc.class);
		} catch (JsonParseException | JsonMappingException e) {
			logger.error("Could not parse XBMC message. " + e.getMessage(), e);
			if (logger.isTraceEnabled()) {
				logger.exit();
			}
			return;
		}
		if (listener != null) {
			final OnMessageReceivedListener listenerFinal = listener;
			final JsonRpc finalMessage = message;
			// TODO Consider upgrading this to an Observable or use an executor. May not be necessary if this doesn't block the client.
			new Thread() {
				
				@Override
				public void run() {
					listenerFinal.onMessageReceived(websocketUri, finalMessage);
				}
				
			}.start();
		}
		if (logger.isTraceEnabled()) {
			logger.exit();
		}
    }
	
	private synchronized void connect(final URI uri) throws Exception {
		if (logger.isTraceEnabled()) {
			logger.entry(uri);
		}
		stopRequested = false;
		websocketUri = uri;
		client.start();
	    
		final ClientUpgradeRequest request = new ClientUpgradeRequest();
		IOException lastException = null;
	    int retries = 1;
	    Random random = new Random();
	    // TODO See how jetty websockets handles the threading. We may need to do this in a separate thread.
	    do {
	    	logger.info("Connecting to " + uri);
		    try {
		    	client.connect(this, uri, request);
		    	logger.info("Connected to " + uri);
		    	lastException = null;
		    } catch (IOException e) {
		    	logger.error("Could not connect to " + uri + ". " + e.getMessage(), e);
		    	lastException = e;
		    	int slotsToSleep = random.nextInt((2 << retries) - 1);
		    	try {
		    		final long sleep = slotsToSleep * RETRY_SLOT_INTERVAL;
		    		if (logger.isDebugEnabled()) {
		    			logger.debug("Waiting " + sleep + " ms to try connecting again.");
		    		}
		    		Thread.sleep(sleep);
		    	} catch (InterruptedException e2) {
		    		logger.error("Connection retry interrupted. Stopping.");
		    		// Interrupted
		    		stopRequested = true;
		    	}
		    	if (retries > 10) {
		    		retries = 1;
		    	} else {
		    		retries++;
		    	}
		    }
	    } while (!stopRequested && lastException != null);
	    

	    if (logger.isTraceEnabled()) {
			logger.exit();
		}
	}
	
	public synchronized void connect(String hostname, int port) throws Exception {
		if (logger.isTraceEnabled()) {
			logger.entry(hostname, port);
		}
		final String uriString = "ws://" + hostname + ":" + port + "/jsonrpc";
		final URI uri = new URI(uriString);
        connect(uri);
        if (logger.isTraceEnabled()) {
			logger.exit();
		}
	}
	
	public synchronized void disconnect() throws Exception {
		if (logger.isTraceEnabled()) {
			logger.entry();
		}
		stopRequested = true;
		if (client.isRunning()) {
			client.stop();
		}
		if (logger.isTraceEnabled()) {
			logger.exit();
		}
	}
	
	 public OnMessageReceivedListener getListener() {
		return listener;
	}
	 
	public void setListener(OnMessageReceivedListener listener) {
		this.listener = listener;
	}
	 
	public Future<Void> sendMessage(String message) {
		if (logger.isTraceEnabled()) {
			logger.entry(message);
		}
		final Future<Void> result = session.getRemote().sendStringByFuture(message);
		if (logger.isTraceEnabled()) {
			logger.exit(result);
		}
		return result;
	}
	
	public Future<Void> sendMessage(JsonRpc jsonRpc) throws JsonProcessingException {
		if (logger.isTraceEnabled()) {
			logger.entry(jsonRpc);
		}
		Future<Void> result;
		if (session != null) {
			final ObjectMapper mapper = new ObjectMapper();
			String message = mapper.writeValueAsString(jsonRpc);
			result = session.getRemote().sendStringByFuture(message);
		} else {
			result = new Future<Void>() {

				@Override
				public boolean cancel(boolean mayInterruptIfRunning) {
					return false;
				}

				@Override
				public boolean isCancelled() {
					return false;
				}

				@Override
				public boolean isDone() {
					return true;
				}

				@Override
				public Void get() throws InterruptedException, ExecutionException {
					throw new ExecutionException("Session is null.", new NullPointerException());
				}

				@Override
				public Void get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException,
						TimeoutException {
					throw new ExecutionException("Session is null.", new NullPointerException());
				}
				
			};
		}
		if (logger.isTraceEnabled()) {
			logger.exit(result);
		}
		return result;
	}
	
	
	public URI getWebsocketUri() {
		return websocketUri;
	}
	
	public String getHost() {
		return websocketUri.getHost();
	}

	public interface OnMessageReceivedListener {
		
		public void onMessageReceived(URI uri, JsonRpc message);
		
	}
	

}
