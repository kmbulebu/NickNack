package com.github.kmbulebu.nicknack.server.services;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.github.kmbulebu.nicknack.core.attributes.AttributeDefinition;
import com.github.kmbulebu.nicknack.core.states.StateDefinition;

public interface StateDefinitionService {
	
	public Collection<StateDefinition> getStateDefinitions();
	
	public Collection<StateDefinition> getStateDefinitionsByProvider(UUID providerUuid);
	
	public StateDefinition getStateDefinition(UUID uuid);

	public List<AttributeDefinition> getAttributeDefinitions(UUID stateUUID);

	public AttributeDefinition getAttributeDefinition(UUID stateUUID, UUID uuid);

	public Map<String, String> getAttributeDefinitionValues(UUID stateUuid, UUID uuid);

}
