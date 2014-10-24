package com.github.kmbulebu.nicknack.server.services;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.github.kmbulebu.nicknack.core.events.AttributeDefinition;
import com.github.kmbulebu.nicknack.core.events.EventDefinition;

public interface EventDefinitionService {
	
	public Collection<EventDefinition> getEventDefinitions();
	
	public Collection<EventDefinition> getEventDefinitionsByProvider(UUID providerUuid);
	
	public EventDefinition getEventDefinition(UUID uuid);

	public List<AttributeDefinition> getAttributeDefinitions(UUID eventUUID);

	public AttributeDefinition getAttributeDefinition(UUID eventUUID, UUID uuid);

	public Map<String, String> getAttributeDefinitionValues(UUID eventUuid, UUID uuid);

}
