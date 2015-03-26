package com.github.kmbulebu.nicknack.server.services;

import java.util.List;
import java.util.UUID;

import com.github.kmbulebu.nicknack.server.restmodel.StateDefinition;

public interface StateDefinitionsService {

	List<StateDefinition> getAllStateDefinitions();

	List<StateDefinition> getStateDefinitionsByProvider(UUID providerUuid);

	StateDefinition getStateDefinition(UUID uuid);

}
