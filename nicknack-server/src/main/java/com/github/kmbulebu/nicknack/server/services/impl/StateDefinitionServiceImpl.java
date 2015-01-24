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
import com.github.kmbulebu.nicknack.server.services.exceptions.AttributeDefinitionNotFoundException;
import com.github.kmbulebu.nicknack.server.services.exceptions.ProviderNotFoundException;
import com.github.kmbulebu.nicknack.server.services.exceptions.StateDefinitionNotFoundException;


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
		
		final List<StateDefinition> stateDefinitions = new ArrayList<StateDefinition>(set);
		
		if (LOG.isTraceEnabled()) {
			LOG.exit(stateDefinitions);
		}
		return stateDefinitions;
		
	}

	@Override
	public StateDefinition getStateDefinition(final UUID uuid) throws StateDefinitionNotFoundException {
		if (LOG.isTraceEnabled()) {
			LOG.entry(uuid);
		}
		
		final StateDefinition stateDefinition = providerService.getStateDefinitions().get(uuid);
		
		if (stateDefinition == null) {
			throw new StateDefinitionNotFoundException(uuid);
		}
		
		if (LOG.isTraceEnabled()) {
			LOG.exit(stateDefinition);
		}
		return stateDefinition;
	}
	
	@Override
	public List<AttributeDefinition<?>> getAttributeDefinitions(final UUID stateUUID) throws StateDefinitionNotFoundException {
		if (LOG.isTraceEnabled()) {
			LOG.entry(stateUUID);
		}
		
		final StateDefinition stateDefinition = getStateDefinition(stateUUID);
		
		final List<AttributeDefinition<?>> attributeDefinitions = Collections.unmodifiableList(stateDefinition.getAttributeDefinitions());
		
		if (LOG.isTraceEnabled()) {
			LOG.exit(attributeDefinitions);
		}
		return attributeDefinitions;
	}
	
	@Override
	public AttributeDefinition<?> getAttributeDefinition(final UUID stateUUID, final UUID uuid) throws StateDefinitionNotFoundException, AttributeDefinitionNotFoundException {
		if (LOG.isTraceEnabled()) {
			LOG.entry(stateUUID, uuid);
		}
		
		final List<AttributeDefinition<?>> attributeDefinitions = getAttributeDefinitions(stateUUID);
		
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
	public Map<String, String> getAttributeDefinitionValues(UUID stateUuid, UUID uuid) throws ProviderNotFoundException, StateDefinitionNotFoundException, AttributeDefinitionNotFoundException {
		if (LOG.isTraceEnabled()) {
			LOG.entry(stateUuid, uuid);
		}
		
		final Provider provider = providerService.getProviderByStateDefinitionUuid(stateUuid);
		
		if (provider == null) {
			throw new ProviderNotFoundException(StateDefinition.class.getSimpleName(), stateUuid);
		}
		
		getAttributeDefinition(stateUuid, uuid);
		
		final Map<String, String> values = provider.getAttributeDefinitionValues(uuid);
		
		if (LOG.isTraceEnabled()) {
			LOG.exit(values);
		}
		return values;
	}

	@Override
	public Collection<StateDefinition> getStateDefinitionsByProvider(UUID providerUuid) throws ProviderNotFoundException {
		if (LOG.isTraceEnabled()) {
			LOG.entry(providerUuid);
		}
		
		final Provider provider = providerService.getProviders().get(providerUuid);
		
		if (provider == null) {
			throw new ProviderNotFoundException(providerUuid);
		}
		
		final Collection<StateDefinition> stateDefinitions = Collections.unmodifiableCollection(provider.getStateDefinitions());
		
		if (LOG.isTraceEnabled()) {
			LOG.exit(stateDefinitions);
		}
		return stateDefinitions;
	}

}
