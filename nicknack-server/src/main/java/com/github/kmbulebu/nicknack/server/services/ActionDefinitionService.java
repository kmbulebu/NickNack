package com.github.kmbulebu.nicknack.server.services;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import com.github.kmbulebu.nicknack.core.actions.ActionDefinition;
import com.github.kmbulebu.nicknack.core.attributes.AttributeDefinition;
import com.github.kmbulebu.nicknack.server.services.exceptions.ActionDefinitionNotFoundException;
import com.github.kmbulebu.nicknack.server.services.exceptions.AttributeDefinitionNotFoundException;
import com.github.kmbulebu.nicknack.server.services.exceptions.ProviderNotFoundException;

public interface ActionDefinitionService {
	
	public Collection<ActionDefinition> getActionDefinitions();
	
	public Collection<ActionDefinition> getActionDefinitionsByProvider(UUID providerUuid) throws ProviderNotFoundException;
	
	public ActionDefinition getActionDefinition(UUID uuid) throws ActionDefinitionNotFoundException;

	public List<AttributeDefinition<?>> getAttributeDefinitions(UUID actionUUID) throws ActionDefinitionNotFoundException;

	public AttributeDefinition<?> getAttributeDefinition(UUID actionUUID, UUID uuid) throws ActionDefinitionNotFoundException, AttributeDefinitionNotFoundException;

}
