package com.github.kmbulebu.nicknack.server.services.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.kmbulebu.nicknack.core.attributes.AttributeDefinition;
import com.github.kmbulebu.nicknack.core.states.StateDefinition;
import com.github.kmbulebu.nicknack.core.providers.Provider;
import com.github.kmbulebu.nicknack.core.providers.ProviderService;
import com.github.kmbulebu.nicknack.server.Application;
import com.github.kmbulebu.nicknack.server.services.StateDefinitionService;


@Service
public class StateDefinitionServiceImpl implements StateDefinitionService {
	
	private static final Logger LOG = LogManager.getLogger(Application.APP_LOGGER_NAME);
	
	@Autowired
	private ProviderService providerService;


	@Override
	public List<StateDefinition> getStateDefinitions() {
		if (LOG.isTraceEnabled()) {
			LOG.entry();
		}
		
		final Collection<StateDefinition> set = providerService.getStateDefinitions().values();
		
		// TODO Add exceptions for not found, etc.
		
		final List<StateDefinition> stateDefinitions = new ArrayList<StateDefinition>(set);
		
		if (LOG.isTraceEnabled()) {
			LOG.exit(stateDefinitions);
		}
		return stateDefinitions;
		
	}

	@Override
	public StateDefinition getStateDefinition(final UUID uuid) {
		if (LOG.isTraceEnabled()) {
			LOG.entry(uuid);
		}
		
		final StateDefinition stateDefinition = providerService.getStateDefinitions().get(uuid);
		
		// TODO Add exceptions for not found, etc.
		
		if (LOG.isTraceEnabled()) {
			LOG.exit(stateDefinition);
		}
		return stateDefinition;
	}
	
	@Override
	public List<AttributeDefinition> getAttributeDefinitions(final UUID stateUUID) {
		if (LOG.isTraceEnabled()) {
			LOG.entry(stateUUID);
		}
		
		final StateDefinition stateDefinition = getStateDefinition(stateUUID);
				
		// TODO Add exceptions for not found, etc.
		
		final List<AttributeDefinition> attributeDefinitions = Collections.unmodifiableList(stateDefinition.getAttributeDefinitions());
		
		if (LOG.isTraceEnabled()) {
			LOG.exit(attributeDefinitions);
		}
		return attributeDefinitions;
	}
	
	@Override
	public AttributeDefinition getAttributeDefinition(final UUID stateUUID, final UUID uuid) {
		if (LOG.isTraceEnabled()) {
			LOG.entry(stateUUID, uuid);
		}
		
		final List<AttributeDefinition> attributeDefinitions = getAttributeDefinitions(stateUUID);
		
		AttributeDefinition attributeDefinition = null;
		
		for (AttributeDefinition anAttributeDefinition : attributeDefinitions) {
			if (uuid.equals(anAttributeDefinition.getUUID())) {
				attributeDefinition = anAttributeDefinition;
				break;
			}
		}
		
		if (LOG.isTraceEnabled()) {
			LOG.exit(attributeDefinition);
		}
		return attributeDefinition;
	}

	@Override
	public Map<String, String> getAttributeDefinitionValues(UUID stateUuid, UUID uuid) {
		if (LOG.isTraceEnabled()) {
			LOG.entry(stateUuid, uuid);
		}
		
		final Provider provider = providerService.getProviderByStateDefinitionUuid(stateUuid);
		
		final Map<String, String> values = provider.getAttributeDefinitionValues(stateUuid, uuid);
		
		if (LOG.isTraceEnabled()) {
			LOG.exit(values);
		}
		return values;
	}

	@Override
	public Collection<StateDefinition> getStateDefinitionsByProvider(UUID providerUuid) {
		if (LOG.isTraceEnabled()) {
			LOG.entry(providerUuid);
		}
		
		final Collection<StateDefinition> stateDefinitions = Collections.unmodifiableCollection(providerService.getProviders().get(providerUuid).getStateDefinitions());
		
		if (LOG.isTraceEnabled()) {
			LOG.exit(stateDefinitions);
		}
		return stateDefinitions;
	}

}
