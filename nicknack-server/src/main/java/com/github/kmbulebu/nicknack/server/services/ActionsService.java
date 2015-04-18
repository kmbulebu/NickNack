package com.github.kmbulebu.nicknack.server.services;

import java.util.List;

import com.github.kmbulebu.nicknack.server.restmodel.Action;

public interface ActionsService {

	public void saveAction(Action action);
	
	public Action getAction(Long id);

	public List<Action> getAllActions();
	
	public void deleteAction(Long id);
	
}
