package com.github.kmbulebu.nicknack.server.services.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.kmbulebu.nicknack.core.actions.ActionDefinition;
import com.github.kmbulebu.nicknack.core.attributes.AttributeDefinition;
import com.github.kmbulebu.nicknack.core.providers.Provider;
import com.github.kmbulebu.nicknack.core.providers.ProviderService;
import com.github.kmbulebu.nicknack.server.Application;
import com.github.kmbulebu.nicknack.server.services.ActionDefinitionService;
import com.github.kmbulebu.nicknack.server.services.exceptions.ActionDefinitionNotFoundException;
import com.github.kmbulebu.nicknack.server.services.exceptions.AttributeDefinitionNotFoundException;
import com.github.kmbulebu.nicknack.server.services.exceptions.ProviderNotFoundException;


@Service
public class ActionDefinitionServiceImpl implements ActionDefinitionService {
	
	private static final Logger LOG = LogManager.getLogger(Application.APP_LOGGER_NAME);
	
	@Autowired
	private ProviderService providerService;


	@Override
	public List<ActionDefinition> getActionDefinitions() {
		if (LOG.isTraceEnabled()) {
			LOG.entry();
		}
		
		final Collection<ActionDefinition> set = providerService.getActionDefinitions().values();

		final List<ActionDefinition> actionDefinitions = new ArrayList<ActionDefinition>(set);
		
		if (LOG.isTraceEnabled()) {
			LOG.exit(actionDefinitions);
		}
		return actionDefinitions;
		
	}

	@Override
	public ActionDefinition getActionDefinition(final UUID uuid) throws ActionDefinitionNotFoundException {
		if (LOG.isTraceEnabled()) {
			LOG.entry(uuid);
		}
		
		final ActionDefinition actionDefinition = providerService.getActionDefinitions().get(uuid);
		
		if (actionDefinition == null) {
			throw new ActionDefinitionNotFoundException(uuid);
		}
		
		if (LOG.isTraceEnabled()) {
			LOG.exit(actionDefinition);
		}
		return actionDefinition;
	}
	
	@Override
	public List<AttributeDefinition<?>> getAttributeDefinitions(final UUID actionUUID) throws ActionDefinitionNotFoundException {
		if (LOG.isTraceEnabled()) {
			LOG.entry(actionUUID);
		}
		
		final ActionDefinition actionDefinition = getActionDefinition(actionUUID);
				
		final List<AttributeDefinition<?>> attributeDefinitions = Collections.unmodifiableList(actionDefinition.getAttributeDefinitions());
		
		if (LOG.isTraceEnabled()) {
			LOG.exit(attributeDefinitions);
		}
		return attributeDefinitions;
	}
	
	@Override
	public AttributeDefinition<?> getAttributeDefinition(final UUID actionUUID, final UUID uuid) throws ActionDefinitionNotFoundException, AttributeDefinitionNotFoundException {
		if (LOG.isTraceEnabled()) {
			LOG.entry(actionUUID, uuid);
		}
		
		final List<AttributeDefinition<?>> attributeDefinitions = getAttributeDefinitions(actionUUID);
		
		AttributeDefinition<?> attributeDefinition = null;
		
		for (AttributeDefinition<?> anAttributeDefinition : attributeDefinitions) {
			if (uuid.equals(anAttributeDefinition.getUUID())) {
				attributeDefinition = anAttributeDefinition;
				break;
			}
		}
		
		if (attributeDefinition == null) {
			throw new AttributeDefinitionNotFoundException(uuid);
		}
		
		if (LOG.isTraceEnabled()) {
			LOG.exit(attributeDefinition);
		}
		return attributeDefinition;
	}

	@Override
	public Collection<ActionDefinition> getActionDefinitionsByProvider(UUID providerUuid) throws ProviderNotFoundException {
		if (LOG.isTraceEnabled()) {
			LOG.entry();
		}
		
		final Provider provider = providerService.getProviders().get(providerUuid);
		
		if (provider == null) {
			throw new ProviderNotFoundException(providerUuid);
		}
		
		final Collection<ActionDefinition> actionDefinitions = Collections.unmodifiableCollection(provider.getActionDefinitions());
	
		if (LOG.isTraceEnabled()) {
			LOG.exit(actionDefinitions);
		}
		return actionDefinitions;
	}

}
