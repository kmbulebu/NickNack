package com.github.kmbulebu.nicknack.providers.pushover.internal;

import java.io.IOException;

public interface PushOverClient {
	
	public void setApiKey(String apiKey);

	public void sendPushMessage(PushMessage pushMessage) throws PushOverException, IOException;

}
