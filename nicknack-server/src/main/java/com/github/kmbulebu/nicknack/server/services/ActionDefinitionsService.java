package com.github.kmbulebu.nicknack.server.services;

import java.util.List;
import java.util.UUID;

import com.github.kmbulebu.nicknack.server.restmodel.ActionDefinition;

public interface ActionDefinitionsService {

	List<ActionDefinition> getAllActionDefinitions();

	List<ActionDefinition> getActionDefinitionsByProvider(UUID providerUuid);

	ActionDefinition getActionDefinition(UUID uuid);

}
