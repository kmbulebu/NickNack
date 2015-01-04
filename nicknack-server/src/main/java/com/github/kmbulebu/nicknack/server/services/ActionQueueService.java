package com.github.kmbulebu.nicknack.server.services;

import java.util.List;
import java.util.UUID;

import com.github.kmbulebu.nicknack.core.actions.Action;
import com.github.kmbulebu.nicknack.server.services.exceptions.ActionNotFoundException;

public interface ActionQueueService {

	public void enqueueAction(Action action);
	
	public void enqueueAction(UUID actionShortcutUuid) throws ActionNotFoundException;
	
	public List<Action> getQueue();

}
