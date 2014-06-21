package com.oakcity.nicknack.providers.xbmc;

import java.util.concurrent.Future;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

@WebSocket
public class XbmcClient {

	private Session session;

	@OnWebSocketClose
	public void onClose(int statusCode, String reason) {
		this.session = null;
		// this.closeLatch.countDown();
	}

	@OnWebSocketConnect
	public void onConnect(Session session) {
		this.session = session;
	}
	
	@OnWebSocketMessage
    public void onMessage(String msg) {
        System.out.printf("Got msg: %s%n", msg);
    }

}
