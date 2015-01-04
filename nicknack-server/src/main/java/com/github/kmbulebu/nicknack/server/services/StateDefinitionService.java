package com.github.kmbulebu.nicknack.server.services;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.github.kmbulebu.nicknack.core.attributes.AttributeDefinition;
import com.github.kmbulebu.nicknack.core.states.StateDefinition;
import com.github.kmbulebu.nicknack.server.services.exceptions.AttributeDefinitionNotFoundException;
import com.github.kmbulebu.nicknack.server.services.exceptions.ProviderNotFoundException;
import com.github.kmbulebu.nicknack.server.services.exceptions.StateDefinitionNotFoundException;

public interface StateDefinitionService {
	
	public Collection<StateDefinition> getStateDefinitions();
	
	public Collection<StateDefinition> getStateDefinitionsByProvider(UUID providerUuid) throws ProviderNotFoundException;
	
	public StateDefinition getStateDefinition(UUID uuid) throws StateDefinitionNotFoundException;

	public List<AttributeDefinition> getAttributeDefinitions(UUID stateUUID) throws StateDefinitionNotFoundException;

	public AttributeDefinition getAttributeDefinition(UUID stateUUID, UUID uuid) throws StateDefinitionNotFoundException, AttributeDefinitionNotFoundException;

	public Map<String, String> getAttributeDefinitionValues(UUID stateUuid, UUID uuid) throws StateDefinitionNotFoundException, ProviderNotFoundException, AttributeDefinitionNotFoundException;

}
