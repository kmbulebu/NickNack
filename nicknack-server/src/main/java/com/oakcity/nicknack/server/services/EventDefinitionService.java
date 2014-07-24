package com.oakcity.nicknack.server.services;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.oakcity.nicknack.core.events.AttributeDefinition;
import com.oakcity.nicknack.core.events.EventDefinition;

public interface EventDefinitionService {
	
	public List<EventDefinition> getEventDefinitions();
	
	public List<EventDefinition> getEventDefinitionsByProvider(UUID providerUuid);
	
	public EventDefinition getEventDefinition(UUID uuid);

	public List<AttributeDefinition> getAttributeDefinitions(UUID eventUUID);

	public AttributeDefinition getAttributeDefinition(UUID eventUUID, UUID uuid);

	public Map<String, String> getAttributeDefinitionValues(UUID eventUuid, UUID uuid);

}
