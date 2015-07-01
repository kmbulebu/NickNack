package com.github.kmbulebu.nicknack.server.services;

import com.github.kmbulebu.nicknack.server.restmodel.Action;

public interface ActionQueueService {
	
	public void enqueueAction(Long actionId);
	
	public void enqueueAction(Action action);

}
