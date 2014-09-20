package com.oakcity.nicknack.providers.xbmc.actions;

import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.oakcity.nicknack.core.actions.Action;
import com.oakcity.nicknack.core.actions.ActionFailureException;
import com.oakcity.nicknack.core.actions.ActionParameterException;
import com.oakcity.nicknack.providers.xbmc.XbmcClient;
import com.oakcity.nicknack.providers.xbmc.XbmcProvider;
import com.oakcity.nicknack.providers.xbmc.actions.parameters.DurationParameterDefinition;
import com.oakcity.nicknack.providers.xbmc.actions.parameters.MessageParameterDefinition;
import com.oakcity.nicknack.providers.xbmc.actions.parameters.NotificationIconParameterDefinition;
import com.oakcity.nicknack.providers.xbmc.actions.parameters.TitleParameterDefinition;
import com.oakcity.nicknack.providers.xbmc.json.JsonRpc;

public class ShowNotificationActionDefinition extends XbmcActionDefinition {
	
	private static final Logger logger = LogManager.getLogger(XbmcProvider.LOGGER_NAME);
	
	public static final ShowNotificationActionDefinition INSTANCE = new ShowNotificationActionDefinition();

	public ShowNotificationActionDefinition() {
		super(UUID.fromString("03bf82ea-975a-4ba5-a41e-59c81455402b"), "Show Notification",
				TitleParameterDefinition.INSTANCE,
				MessageParameterDefinition.INSTANCE,
				NotificationIconParameterDefinition.INSTANCE,
				DurationParameterDefinition.INSTANCE);
	}

	private static final String METHOD = "GUI.ShowNotification";
	
	@Override
	public void run(Action action, XbmcClient client) throws ActionFailureException, ActionParameterException {
		if (logger.isTraceEnabled()) {
			logger.entry(action, client);
		}
		final String title = action.getParameters().get(TitleParameterDefinition.DEF_UUID);
		if (title == null) {
			final ActionParameterException t = new ActionParameterException(TitleParameterDefinition.INSTANCE.getName() + " is missing.");
			if (logger.isTraceEnabled()) {
				logger.throwing(t);
			}
			throw t;
		}
		
		final String message = action.getParameters().get(MessageParameterDefinition.DEF_UUID);
		if (message == null) {
			final ActionParameterException t = new ActionParameterException(MessageParameterDefinition.INSTANCE.getName() + " is missing.");
			if (logger.isTraceEnabled()) {
				logger.throwing(t);
			}
			throw t;
		}
		
		final String icon = action.getParameters().get(NotificationIconParameterDefinition.DEF_UUID);
		if (icon == null) {
			final ActionParameterException t = new ActionParameterException(NotificationIconParameterDefinition.INSTANCE.getName() + " is missing.");
			if (logger.isTraceEnabled()) {
				logger.throwing(t);
			}
			throw t;
		}
		
		final String durationStr = action.getParameters().get(DurationParameterDefinition.DEF_UUID);
		if (durationStr == null) {
			final ActionParameterException t = new ActionParameterException(DurationParameterDefinition.INSTANCE.getName() + " is missing.");
			if (logger.isTraceEnabled()) {
				logger.throwing(t);
			}
			throw t;
		}
		
		final int duration = Integer.parseInt(durationStr);
				
		final JsonRpc rpcMessage = new JsonRpc();
		rpcMessage.setId(Long.toString(Thread.currentThread().getId()));
		rpcMessage.setMethod(METHOD);
		final ObjectNode params = JsonNodeFactory.instance.objectNode();
		params.put("title", title);
		params.put("message", message);
		params.put("image", icon);
		params.put("displaytime", duration);
		rpcMessage.setParams(params);
		try {
			client.sendMessage(rpcMessage);
		} catch (JsonProcessingException e) {
			final ActionFailureException t = new ActionFailureException("Failed to build show notification message.", e);
			if (logger.isTraceEnabled()) {
				logger.throwing(t);
			}
			throw t;
		}
		if (logger.isTraceEnabled()) {
			logger.exit();
		}
	}

}
