package com.github.kmbulebu.nicknack.server.services;

import java.util.List;
import java.util.UUID;

import com.github.kmbulebu.nicknack.server.model.ActionResource;
import com.github.kmbulebu.nicknack.server.services.exceptions.ActionNotFoundException;
import com.github.kmbulebu.nicknack.server.services.exceptions.PlanNotFoundException;

public interface ActionsService {
	
	public List<ActionResource> getActionsByPlan(UUID planUuid) throws PlanNotFoundException;
	
	public void addActionToPlan(UUID planUuid, UUID actionUuid) throws ActionNotFoundException, PlanNotFoundException;
	
	public void deleteAction(UUID planUuid, UUID actionUuid) throws PlanNotFoundException, ActionNotFoundException;
	
	public List<ActionResource> getActions();
	
	public ActionResource getAction(UUID uuid) throws ActionNotFoundException;
	
	public void deleteAction(UUID uuid) throws ActionNotFoundException;
	
	public ActionResource createAction(ActionResource newAction);
	
	public ActionResource modifyAction(ActionResource modifiedAction) throws ActionNotFoundException;


}
