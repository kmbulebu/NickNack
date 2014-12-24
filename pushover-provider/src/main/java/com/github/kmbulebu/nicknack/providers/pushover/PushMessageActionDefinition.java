package com.github.kmbulebu.nicknack.providers.pushover;

import java.io.IOException;
import java.util.Date;
import java.util.UUID;

import com.github.kmbulebu.nicknack.core.actions.Action;
import com.github.kmbulebu.nicknack.core.actions.ActionFailureException;
import com.github.kmbulebu.nicknack.core.actions.ActionParameterException;

public class PushMessageActionDefinition extends AbstractPushMessageActionDefinition {
	
	public static final UUID DEF_UUID = UUID.fromString("07d8e418-8a54-4b60-bd5b-13d509191d7b");
	
	public PushMessageActionDefinition() {
		super(DEF_UUID, "Send Push Message",
				TitleParameterDefinition.INSTANCE,
				DeviceParameterDefinition.INSTANCE,
				UrlParameterDefinition.INSTANCE,
				UrlTitleParameterDefinition.INSTANCE,
				PriorityParameterDefinition.INSTANCE,
				SoundParameterDefinition.INSTANCE);
	}
	

	@Override
	public void run(Action action) throws ActionFailureException, ActionParameterException {
		final PushMessage pushMessage = new PushMessage();
		pushMessage.setTimeStamp(new Date());
		pushMessage.setMessage(action.getParameters().get(MessageParameterDefinition.DEF_UUID));
		pushMessage.setUser(action.getParameters().get(UserParameterDefinition.DEF_UUID));
		pushMessage.setTitle(action.getParameters().get(TitleParameterDefinition.DEF_UUID));
		pushMessage.setDevice(action.getParameters().get(DeviceParameterDefinition.DEF_UUID));
		pushMessage.setUrl(action.getParameters().get(UrlParameterDefinition.DEF_UUID));
		pushMessage.setUrlTitle(action.getParameters().get(UrlTitleParameterDefinition.DEF_UUID));
		pushMessage.setSound(action.getParameters().get(SoundParameterDefinition.DEF_UUID));
		
		final String priorityStr = action.getParameters().get(PriorityParameterDefinition.DEF_UUID);
		if (priorityStr != null && priorityStr.matches("-?\\d+")) {
			pushMessage.setPriority(Integer.parseInt(priorityStr));
		}
		
		final String apiToken = action.getParameters().get(TokenParameterDefinition.DEF_UUID);
		
		if (apiToken == null) {
			throw new ActionParameterException("Token is required.");
		}
		
		if (pushMessage.getUser() == null) {
			throw new ActionParameterException("User is required.");
		}
		
		if (pushMessage.getMessage() == null) {
			throw new ActionParameterException("Message is required.");
		}
		
		PushOverClient pushOverClient = new PushOverClientImpl();
		pushOverClient.setApiKey(apiToken);
		
		try {
			pushOverClient.sendPushMessage(pushMessage);
		} catch (PushOverException e) {
			throw new ActionParameterException(e.getMessage(), e);
		} catch (IOException e) {
			throw new ActionFailureException(e.getMessage(), e);
		}
 	}

}
