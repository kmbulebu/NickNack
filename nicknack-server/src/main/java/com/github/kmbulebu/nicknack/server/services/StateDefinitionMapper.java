package com.github.kmbulebu.nicknack.server.services;

import com.github.kmbulebu.nicknack.server.restmodel.StateDefinition;

public interface StateDefinitionMapper {

	public StateDefinition map(com.github.kmbulebu.nicknack.core.states.StateDefinition coreStateDefinition);

}