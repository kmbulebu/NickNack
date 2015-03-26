package com.github.kmbulebu.nicknack.providers.pushover.actions;

import java.io.IOException;
import java.util.Date;
import java.util.UUID;

import com.github.kmbulebu.nicknack.core.actions.Action;
import com.github.kmbulebu.nicknack.core.actions.ActionFailureException;
import com.github.kmbulebu.nicknack.core.actions.ActionAttributeException;
import com.github.kmbulebu.nicknack.providers.pushover.attributes.DeviceAttributeDefinition;
import com.github.kmbulebu.nicknack.providers.pushover.attributes.MessageAttributeDefinition;
import com.github.kmbulebu.nicknack.providers.pushover.attributes.PriorityAttributeDefinition;
import com.github.kmbulebu.nicknack.providers.pushover.attributes.SoundAttributeDefinition;
import com.github.kmbulebu.nicknack.providers.pushover.attributes.TitleAttributeDefinition;
import com.github.kmbulebu.nicknack.providers.pushover.attributes.TokenAttributeDefinition;
import com.github.kmbulebu.nicknack.providers.pushover.attributes.UrlAttributeDefinition;
import com.github.kmbulebu.nicknack.providers.pushover.attributes.UrlTitleAttributeDefinition;
import com.github.kmbulebu.nicknack.providers.pushover.attributes.UserAttributeDefinition;
import com.github.kmbulebu.nicknack.providers.pushover.internal.PushMessage;
import com.github.kmbulebu.nicknack.providers.pushover.internal.PushOverClient;
import com.github.kmbulebu.nicknack.providers.pushover.internal.PushOverClientImpl;
import com.github.kmbulebu.nicknack.providers.pushover.internal.PushOverException;

public class PushMessageActionDefinition extends AbstractPushMessageActionDefinition {
	
	public static final UUID DEF_UUID = UUID.fromString("07d8e418-8a54-4b60-bd5b-13d509191d7b");
	
	public PushMessageActionDefinition() {
		super(DEF_UUID, "Send Push Message",
				"Send a push message to a mobile device or desktop computer using the Pushover service.",
				TitleAttributeDefinition.INSTANCE,
				DeviceAttributeDefinition.INSTANCE,
				UrlAttributeDefinition.INSTANCE,
				UrlTitleAttributeDefinition.INSTANCE,
				PriorityAttributeDefinition.INSTANCE,
				SoundAttributeDefinition.INSTANCE);
	}
	

	@Override
	public void run(Action action) throws ActionFailureException, ActionAttributeException {
		final PushMessage pushMessage = new PushMessage();
		pushMessage.setTimeStamp(new Date());
		pushMessage.setMessage(action.getAttributes().get(MessageAttributeDefinition.DEF_UUID));
		pushMessage.setUser(action.getAttributes().get(UserAttributeDefinition.DEF_UUID));
		pushMessage.setTitle(action.getAttributes().get(TitleAttributeDefinition.DEF_UUID));
		pushMessage.setDevice(action.getAttributes().get(DeviceAttributeDefinition.DEF_UUID));
		pushMessage.setUrl(action.getAttributes().get(UrlAttributeDefinition.DEF_UUID));
		pushMessage.setUrlTitle(action.getAttributes().get(UrlTitleAttributeDefinition.DEF_UUID));
		pushMessage.setSound(action.getAttributes().get(SoundAttributeDefinition.DEF_UUID));
		
		final String priorityStr = action.getAttributes().get(PriorityAttributeDefinition.DEF_UUID);
		if (priorityStr != null && priorityStr.matches("-?\\d+")) {
			pushMessage.setPriority(Integer.parseInt(priorityStr));
		}
		
		final String apiToken = action.getAttributes().get(TokenAttributeDefinition.DEF_UUID);
		
		if (apiToken == null) {
			throw new ActionAttributeException("Token is required.");
		}
		
		if (pushMessage.getUser() == null) {
			throw new ActionAttributeException("User is required.");
		}
		
		if (pushMessage.getMessage() == null) {
			throw new ActionAttributeException("Message is required.");
		}
		
		PushOverClient pushOverClient = new PushOverClientImpl();
		pushOverClient.setApiKey(apiToken);
		
		try {
			pushOverClient.sendPushMessage(pushMessage);
		} catch (PushOverException e) {
			throw new ActionAttributeException(e.getMessage(), e);
		} catch (IOException e) {
			throw new ActionFailureException(e.getMessage(), e);
		}
 	}

}
