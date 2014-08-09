package com.oakcity.nicknack.providers.xbmc.actions;

import java.util.UUID;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.oakcity.nicknack.core.actions.Action;
import com.oakcity.nicknack.core.actions.ActionFailureException;
import com.oakcity.nicknack.core.actions.ActionParameterException;
import com.oakcity.nicknack.providers.xbmc.XbmcClient;
import com.oakcity.nicknack.providers.xbmc.actions.parameters.DurationParameterDefinition;
import com.oakcity.nicknack.providers.xbmc.actions.parameters.MessageParameterDefinition;
import com.oakcity.nicknack.providers.xbmc.actions.parameters.NotificationIconParameterDefinition;
import com.oakcity.nicknack.providers.xbmc.actions.parameters.TitleParameterDefinition;
import com.oakcity.nicknack.providers.xbmc.json.JsonRpc;

public class ShowNotificationActionDefinition extends XbmcActionDefinition {
	
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
		final String title = action.getParameters().get(TitleParameterDefinition.DEF_UUID);
		if (title == null) {
			throw new ActionParameterException(TitleParameterDefinition.INSTANCE.getName() + " is missing.");
		}
		
		final String message = action.getParameters().get(MessageParameterDefinition.DEF_UUID);
		if (message == null) {
			throw new ActionParameterException(MessageParameterDefinition.INSTANCE.getName() + " is missing.");
		}
		
		final String icon = action.getParameters().get(NotificationIconParameterDefinition.DEF_UUID);
		if (icon == null) {
			throw new ActionParameterException(NotificationIconParameterDefinition.INSTANCE.getName() + " is missing.");
		}
		
		final String durationStr = action.getParameters().get(DurationParameterDefinition.DEF_UUID);
		if (durationStr == null) {
			throw new ActionParameterException(DurationParameterDefinition.INSTANCE.getName() + " is missing.");
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
			throw new ActionFailureException(e);
		}
	}

}
