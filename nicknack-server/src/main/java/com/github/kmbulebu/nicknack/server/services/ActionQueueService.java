package com.github.kmbulebu.nicknack.server.services;

import java.util.List;
import java.util.UUID;

import com.github.kmbulebu.nicknack.core.actions.Action;

public interface ActionQueueService {

	public void enqueueAction(Action action);
	
	public void enqueueAction(UUID actionShortcutUuid);
	
	public List<Action> getQueue();

}
