package com.oakcity.nicknack.providers.xbmc;

import java.io.IOException;
import java.net.URI;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

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
import com.oakcity.nicknack.providers.xbmc.json.JsonRpc;

@WebSocket
public class XbmcClient {

	// Used for sending messages.
	private Session session;
	private final ObjectMapper mapper = new ObjectMapper();
	private final WebSocketClient client = new WebSocketClient();
	private URI websocketUri = null;
	
	private boolean stopRequested = false;
	
	private OnMessageReceivedListener listener;

	@OnWebSocketClose
	public void onClose(int statusCode, String reason) throws Exception {
		this.session = null;
		if (!stopRequested && websocketUri != null) {
			connect(websocketUri);
		}
	}

	@OnWebSocketConnect
	public void onConnect(Session session) {
		this.session = session;
	}
	
	@OnWebSocketMessage
    public void onMessage(String msg) throws IOException {
		if (msg == null || msg.isEmpty()) {
			return;
		}
		System.out.println(msg);
		JsonRpc message;
		try {
			message = mapper.readValue(msg, JsonRpc.class);
		} catch (JsonParseException | JsonMappingException e) {
			System.out.println(msg);
			// TODO Auto-generated catch block
			e.printStackTrace();
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
    }
	
	private void connect(URI uri) throws Exception {
		stopRequested = false;
		websocketUri = uri;
		client.start();
	    ClientUpgradeRequest request = new ClientUpgradeRequest();
	    client.connect(this, uri, request);
	}
	
	public void connect(String hostname, int port) throws Exception {
		final String uriString = "ws://" + hostname + ":" + port + "/jsonrpc";
		final URI uri = new URI(uriString);
        connect(uri);
	}
	
	public void disconnect() throws Exception {
		stopRequested = true;
		client.stop();
	}
	
	 public OnMessageReceivedListener getListener() {
		return listener;
	}
	 
	public void setListener(OnMessageReceivedListener listener) {
		this.listener = listener;
	}
	 
	public Future<Void> sendMessage(String message) {
		return session.getRemote().sendStringByFuture(message);
	}
	
	public Future<Void> sendMessage(JsonRpc jsonRpc) throws JsonProcessingException {
		if (session != null) {
			final ObjectMapper mapper = new ObjectMapper();
			String message = mapper.writeValueAsString(jsonRpc);
			return session.getRemote().sendStringByFuture(message);
		} else {
			return new Future<Void>() {

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
