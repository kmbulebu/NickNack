package com.oakcity.nicknack.providers.xbmc;

import java.io.IOException;
import java.net.URI;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.eclipse.jetty.websocket.client.ClientUpgradeRequest;
import org.eclipse.jetty.websocket.client.WebSocketClient;

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
		System.out.println("Connected to XBMC!");
		this.session = session;
	}
	
	@OnWebSocketMessage
    public void onMessage(String msg) throws IOException {
		final JsonRpc message = mapper.readValue(msg, JsonRpc.class);
		if (listener != null) {
			final OnMessageReceivedListener listenerFinal = listener;
			// TODO Consider upgrading this to an Observable or use an executor. May not be necessary if this doesn't block the client.
			new Thread() {
				
				@Override
				public void run() {
					listenerFinal.onMessageReceived(message);
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

	public interface OnMessageReceivedListener {
		
		public void onMessageReceived(JsonRpc message);
		
	}

}
