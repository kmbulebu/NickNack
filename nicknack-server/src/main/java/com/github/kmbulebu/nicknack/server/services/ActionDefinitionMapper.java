package com.github.kmbulebu.nicknack.server.services;

import java.util.UUID;

import com.github.kmbulebu.nicknack.server.restmodel.ActionDefinition;

public interface ActionDefinitionMapper {

	public ActionDefinition map(com.github.kmbulebu.nicknack.core.actions.ActionDefinition coreActionDefinition, UUID providerUuid);

}