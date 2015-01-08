package com.github.kmbulebu.nicknack.providers.xbmc.actions;

import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.kmbulebu.nicknack.core.actions.Action;
import com.github.kmbulebu.nicknack.core.actions.ActionFailureException;
import com.github.kmbulebu.nicknack.core.actions.ActionParameterException;
import com.github.kmbulebu.nicknack.providers.xbmc.XbmcProvider;
import com.github.kmbulebu.nicknack.providers.xbmc.attributes.DurationAttributeDefinition;
import com.github.kmbulebu.nicknack.providers.xbmc.attributes.MessageAttributeDefinition;
import com.github.kmbulebu.nicknack.providers.xbmc.attributes.NotificationIconAttributeDefinition;
import com.github.kmbulebu.nicknack.providers.xbmc.attributes.TitleAttributeDefinition;
import com.github.kmbulebu.nicknack.providers.xbmc.internal.JsonRpc;
import com.github.kmbulebu.nicknack.providers.xbmc.internal.XbmcClient;

public class ShowNotificationActionDefinition extends AbstractXbmcActionDefinition {
	
	private static final Logger logger = LogManager.getLogger(XbmcProvider.LOGGER_NAME);
	
	public static final ShowNotificationActionDefinition INSTANCE = new ShowNotificationActionDefinition();

	public ShowNotificationActionDefinition() {
		super(UUID.fromString("03bf82ea-975a-4ba5-a41e-59c81455402b"), "Show Notification",
				"Shows a floating notification on the screen.",
				TitleAttributeDefinition.INSTANCE,
				MessageAttributeDefinition.INSTANCE,
				NotificationIconAttributeDefinition.INSTANCE,
				DurationAttributeDefinition.INSTANCE);
	}

	private static final String METHOD = "GUI.ShowNotification";
	
	@Override
	public void run(Action action, XbmcClient client) throws ActionFailureException, ActionParameterException {
		if (logger.isTraceEnabled()) {
			logger.entry(action, client);
		}
		final String title = action.getAttributes().get(TitleAttributeDefinition.DEF_UUID);
		if (title == null) {
			final ActionParameterException t = new ActionParameterException(TitleAttributeDefinition.INSTANCE.getName() + " is missing.");
			if (logger.isTraceEnabled()) {
				logger.throwing(t);
			}
			throw t;
		}
		
		final String message = action.getAttributes().get(MessageAttributeDefinition.DEF_UUID);
		if (message == null) {
			final ActionParameterException t = new ActionParameterException(MessageAttributeDefinition.INSTANCE.getName() + " is missing.");
			if (logger.isTraceEnabled()) {
				logger.throwing(t);
			}
			throw t;
		}
		
		final String icon = action.getAttributes().get(NotificationIconAttributeDefinition.DEF_UUID);
		if (icon == null) {
			final ActionParameterException t = new ActionParameterException(NotificationIconAttributeDefinition.INSTANCE.getName() + " is missing.");
			if (logger.isTraceEnabled()) {
				logger.throwing(t);
			}
			throw t;
		}
		
		final String durationStr = action.getAttributes().get(DurationAttributeDefinition.DEF_UUID);
		if (durationStr == null) {
			final ActionParameterException t = new ActionParameterException(DurationAttributeDefinition.INSTANCE.getName() + " is missing.");
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
