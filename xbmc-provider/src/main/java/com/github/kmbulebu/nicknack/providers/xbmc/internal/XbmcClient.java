package com.github.kmbulebu.nicknack.providers.xbmc.internal;

import java.io.IOException;
import java.net.URI;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.annotation.CheckForNull;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketError;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.eclipse.jetty.websocket.client.ClientUpgradeRequest;
import org.eclipse.jetty.websocket.client.WebSocketClient;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.kmbulebu.nicknack.providers.xbmc.XbmcProvider;

@WebSocket
public class XbmcClient implements Callable<WebSocketClient> {
	
	private static final Logger logger = LogManager.getLogger(XbmcProvider.LOGGER_NAME);

	// Used for sending messages.
	private Session session;
	private final ObjectMapper mapper = new ObjectMapper();
	private WebSocketClient client = null;
	private final URI websocketUri;
	
	private boolean stopRequested = false;
	
	private OnMessageReceivedListener listener;
	
	private static final long RETRY_SLOT_INTERVAL = 1000;
	
	private final ExecutorService connectExecutor = Executors.newSingleThreadExecutor();
	
	public XbmcClient(URI uri) {
		this.websocketUri = uri;
		this.client = new WebSocketClient();
	}
	
	public XbmcClient(String hostname, int port) {
		this(URI.create("ws://" + hostname + ":" + port + "/jsonrpc"));
	}

	@OnWebSocketClose
	public void onClose(int statusCode, String reason) throws Exception {
		if (logger.isTraceEnabled()) {
			logger.entry(statusCode, reason);
		}
		this.session = null;
		this.client = null;
		if (!stopRequested && websocketUri != null) {
			connect();
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
	
	@OnWebSocketError
	public void onError(@CheckForNull Session session, Throwable error) throws Exception {
		if (logger.isTraceEnabled()) {
			logger.entry(session, error);
		}
		
		if (session == null || !session.isOpen()) {
			this.session = null;
			this.client = null;
			if (!stopRequested && websocketUri != null) {
				connect();
			}
		}
		
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
	
	public synchronized void connect() {
		if (logger.isTraceEnabled()) {
			logger.entry();
		}
		stopRequested = false;
		
	    if (!stopRequested) {
	    	final FutureTask<WebSocketClient> futureTask = new FutureTask<WebSocketClient>(this) {
	    		
	    		@Override
	    		protected void done() {
	    			if (!isCancelled()) {
	    				try {
							XbmcClient.this.client = get();
						} catch (InterruptedException e) {
							stopRequested = true;
						} catch (ExecutionException e) {
							if (logger.isWarnEnabled()) {
								logger.warn("Could not connect to XBMC at " + websocketUri + ". " + e.getCause().getMessage(), e.getCause());
							}
						}
	    			}
	    		}
	    	};
	    	connectExecutor.execute(futureTask);
	    
	    }

	    if (logger.isTraceEnabled()) {
			logger.exit();
		}
	}
	
	public synchronized void disconnect() throws Exception {
		if (logger.isTraceEnabled()) {
			logger.entry();
		}
		stopRequested = true;
		
		if (client != null && client.isRunning()) {
			client.stop();
		}
		client = null;
		session = null;
		
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
		if (client != null && session != null) {
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
	

	@Override
	public WebSocketClient call() throws Exception {
		client.setConnectTimeout(2000);
		client.setMaxIdleTimeout(0);
		client.start();
	    
		final ClientUpgradeRequest request = new ClientUpgradeRequest();
		Exception lastException = null;
	    int retries = 1;
	    Random random = new Random();

	    //Retry indefinitely. 
	    do {
	    	logger.info("Connecting to " + websocketUri);
		    try {
		    	client.connect(this, websocketUri, request).get();
		    	logger.info("Connected to " + websocketUri);
		    	lastException = null;
		    } catch (IOException e) {
		    	logger.error("Could not connect to " + websocketUri + ". " + e.getMessage(), e);
		    	lastException = e;
		    	int slotsToSleep = random.nextInt((2 << retries) - 1);
	    		final long sleep = slotsToSleep * RETRY_SLOT_INTERVAL;
	    		if (logger.isDebugEnabled()) {
	    			logger.debug("Waiting " + sleep + " ms to try connecting again.");
	    		}
	    		Thread.sleep(sleep);
		    	if (retries > 10) {
		    		retries = 1;
		    	} else {
		    		retries++;
		    	}
		    } 
	    } while (!Thread.currentThread().isInterrupted() && lastException != null);
	    
	    return client;
	}

}
