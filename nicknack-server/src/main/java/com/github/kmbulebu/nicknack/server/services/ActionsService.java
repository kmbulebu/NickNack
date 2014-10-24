package com.github.kmbulebu.nicknack.server.services;

import java.util.List;
import java.util.UUID;

import com.github.kmbulebu.nicknack.server.model.ActionResource;

public interface ActionsService {
	
	public List<ActionResource> getActions(UUID planUuid);
	
	public ActionResource getAction(UUID uuid);
	
	public void deleteAction(UUID uuid);
	
	public ActionResource createAction(UUID planUuid, ActionResource newAction);
	
	public ActionResource modifyAction(ActionResource modifiedAction);

}
