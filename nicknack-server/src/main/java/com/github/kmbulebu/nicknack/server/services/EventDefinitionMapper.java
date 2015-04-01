package com.github.kmbulebu.nicknack.server.services;

import java.util.UUID;

import com.github.kmbulebu.nicknack.server.restmodel.EventDefinition;

public interface EventDefinitionMapper {

	public EventDefinition map(com.github.kmbulebu.nicknack.core.events.EventDefinition coreEventDefinition, UUID providerUuid);

}