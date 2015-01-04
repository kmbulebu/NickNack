package com.github.kmbulebu.nicknack.server.services;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.github.kmbulebu.nicknack.core.attributes.AttributeDefinition;
import com.github.kmbulebu.nicknack.core.events.EventDefinition;
import com.github.kmbulebu.nicknack.server.services.exceptions.AttributeDefinitionNotFoundException;
import com.github.kmbulebu.nicknack.server.services.exceptions.EventDefinitionNotFoundException;
import com.github.kmbulebu.nicknack.server.services.exceptions.ProviderNotFoundException;

public interface EventDefinitionService {
	
	public Collection<EventDefinition> getEventDefinitions();
	
	public Collection<EventDefinition> getEventDefinitionsByProvider(UUID providerUuid) throws ProviderNotFoundException;
	
	public EventDefinition getEventDefinition(UUID uuid) throws EventDefinitionNotFoundException;

	public List<AttributeDefinition> getAttributeDefinitions(UUID eventUUID) throws EventDefinitionNotFoundException;

	public AttributeDefinition getAttributeDefinition(UUID eventUUID, UUID uuid) throws EventDefinitionNotFoundException, AttributeDefinitionNotFoundException;

	public Map<String, String> getAttributeDefinitionValues(UUID eventUuid, UUID uuid) throws EventDefinitionNotFoundException, ProviderNotFoundException;

}
