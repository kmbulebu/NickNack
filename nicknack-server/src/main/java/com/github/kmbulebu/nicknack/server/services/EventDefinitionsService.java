package com.github.kmbulebu.nicknack.server.services;

import java.util.List;
import java.util.UUID;

import com.github.kmbulebu.nicknack.server.restmodel.EventDefinition;

public interface EventDefinitionsService {

	List<EventDefinition> getAllEventDefinitions();

	List<EventDefinition> getEventDefinitionsByProvider(UUID providerUuid);

	EventDefinition getEventDefinition(UUID uuid);

}
