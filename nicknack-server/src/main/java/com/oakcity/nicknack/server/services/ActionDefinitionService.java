package com.oakcity.nicknack.server.services;

import java.util.List;
import java.util.UUID;

import com.oakcity.nicknack.core.actions.Action.ActionDefinition;
import com.oakcity.nicknack.core.actions.Action.ParameterDefinition;

public interface ActionDefinitionService {
	
	public List<ActionDefinition> getActionDefinitions();
	
	public ActionDefinition getActionDefinition(UUID uuid);

	public List<ParameterDefinition> getParameterDefinitions(UUID actionUUID);

	public ParameterDefinition getParameterDefinition(UUID actionUUID, UUID uuid);

}
