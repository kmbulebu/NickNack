package com.github.kmbulebu.nicknack.server.services;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import com.github.kmbulebu.nicknack.core.actions.ActionDefinition;
import com.github.kmbulebu.nicknack.core.actions.ParameterDefinition;
import com.github.kmbulebu.nicknack.server.services.exceptions.ActionDefinitionNotFoundException;
import com.github.kmbulebu.nicknack.server.services.exceptions.ParameterDefinitionNotFoundException;
import com.github.kmbulebu.nicknack.server.services.exceptions.ProviderNotFoundException;

public interface ActionDefinitionService {
	
	public Collection<ActionDefinition> getActionDefinitions();
	
	public Collection<ActionDefinition> getActionDefinitionsByProvider(UUID providerUuid) throws ProviderNotFoundException;
	
	public ActionDefinition getActionDefinition(UUID uuid) throws ActionDefinitionNotFoundException;

	public List<ParameterDefinition> getParameterDefinitions(UUID actionUUID) throws ActionDefinitionNotFoundException;

	public ParameterDefinition getParameterDefinition(UUID actionUUID, UUID uuid) throws ActionDefinitionNotFoundException, ParameterDefinitionNotFoundException;

}
