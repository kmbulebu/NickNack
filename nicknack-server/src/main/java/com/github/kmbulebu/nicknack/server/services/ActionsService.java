package com.github.kmbulebu.nicknack.server.services;

import java.util.List;
import java.util.UUID;

import com.github.kmbulebu.nicknack.server.model.ActionResource;

public interface ActionsService {
	
	public List<ActionResource> getActionsByPlan(UUID planUuid);
	
	public void addActionToPlan(UUID planUuid, UUID actionUuid);
	
	public void deleteAction(UUID planUuid, UUID actionUuid);
	
	public List<ActionResource> getActions();
	
	public ActionResource getAction(UUID uuid);
	
	public void deleteAction(UUID uuid);
	
	public ActionResource createAction(ActionResource newAction);
	
	public ActionResource modifyAction(ActionResource modifiedAction);


}
