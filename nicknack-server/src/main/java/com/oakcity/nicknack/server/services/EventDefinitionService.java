package com.oakcity.nicknack.server.services;

import java.util.List;
import java.util.UUID;

import com.oakcity.nicknack.core.events.Event.EventDefinition;

public interface EventDefinitionService {
	
	public List<EventDefinition> getEventDefinitions();
	
	public EventDefinition getEventDefinition(UUID uuid);

}
