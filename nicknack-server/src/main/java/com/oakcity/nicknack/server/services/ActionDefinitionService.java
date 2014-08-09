package com.oakcity.nicknack.server.services;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import com.oakcity.nicknack.core.actions.ActionDefinition;
import com.oakcity.nicknack.core.actions.ParameterDefinition;

public interface ActionDefinitionService {
	
	public Collection<ActionDefinition> getActionDefinitions();
	
	public Collection<ActionDefinition> getActionDefinitionsByProvider(UUID providerUuid);
	
	public ActionDefinition getActionDefinition(UUID uuid);

	public List<ParameterDefinition> getParameterDefinitions(UUID actionUUID);

	public ParameterDefinition getParameterDefinition(UUID actionUUID, UUID uuid);

}
