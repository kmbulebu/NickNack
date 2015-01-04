package com.github.kmbulebu.nicknack.server.services.impl;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.kmbulebu.nicknack.core.providers.Provider;
import com.github.kmbulebu.nicknack.core.providers.ProviderService;
import com.github.kmbulebu.nicknack.core.states.State;
import com.github.kmbulebu.nicknack.core.states.StateDefinition;
import com.github.kmbulebu.nicknack.server.Application;
import com.github.kmbulebu.nicknack.server.model.StatesResource;
import com.github.kmbulebu.nicknack.server.services.StatesService;
import com.github.kmbulebu.nicknack.server.services.exceptions.ProviderNotFoundException;
import com.github.kmbulebu.nicknack.server.services.exceptions.StateDefinitionNotFoundException;

@Service
public class StatesServiceImpl implements StatesService{
	
	private static final Logger LOG = LogManager.getLogger(Application.APP_LOGGER_NAME);
	
	@Autowired
	private ProviderService providerService;

	@Override
	public List<StatesResource> getAllStates() {
		if (LOG.isTraceEnabled()) {
			LOG.entry();
		}
		
		final Collection<Provider> providers = providerService.getProviders().values();
		
		final List<StatesResource> resources = new LinkedList<>();

		for (Provider provider : providers) {
			for (StateDefinition stateDefinition : provider.getStateDefinitions()) {
				final List<? extends State> states = provider.getStates(stateDefinition.getUUID());
				final StatesResource resource = new StatesResource();
				resource.setStateDefinition(stateDefinition);
				resource.setStates(states);
				resources.add(resource);
			}
		}
		
		if (LOG.isTraceEnabled()) {
			LOG.exit(resources);
		}
		return resources;
	}

	@Override
	public List<StatesResource> getAllStatesByProvider(UUID providerUuid) throws ProviderNotFoundException {
		if (LOG.isTraceEnabled()) {
			LOG.entry(providerUuid);
		}
		
		final Provider provider = providerService.getProviders().get(providerUuid);
		
		if (provider == null) {
			throw new ProviderNotFoundException(providerUuid);
		}
		
		final List<StatesResource> resources = new LinkedList<>();

		for (StateDefinition stateDefinition : provider.getStateDefinitions()) {
			final List<? extends State> states = provider.getStates(stateDefinition.getUUID());
			final StatesResource resource = new StatesResource();
			resource.setStateDefinition(stateDefinition);
			resource.setStates(states);
			resources.add(resource);
		}
		
		if (LOG.isTraceEnabled()) {
			LOG.exit(resources);
		}
		return resources;
	}

	@Override
	public StatesResource getStates(UUID stateDefinitionUuid) throws ProviderNotFoundException, StateDefinitionNotFoundException {
		if (LOG.isTraceEnabled()) {
			LOG.entry(stateDefinitionUuid);
		}
		
		final Provider provider = providerService.getProviderByStateDefinitionUuid(stateDefinitionUuid);
		
		if (provider == null) {
			throw new ProviderNotFoundException(StateDefinition.class.getSimpleName(), stateDefinitionUuid);
		}
		
		StateDefinition stateDefinition = null;
		for (StateDefinition aDefinition : provider.getStateDefinitions()) {
			if (aDefinition.getUUID().equals(stateDefinitionUuid)) {
				stateDefinition = aDefinition;
				break;
			}
		}
		
		if (stateDefinition == null) {
			throw new StateDefinitionNotFoundException(stateDefinitionUuid);
		}
		
		final List<? extends State> states = provider.getStates(stateDefinitionUuid);
		final StatesResource resource = new StatesResource();
		resource.setStateDefinition(stateDefinition);
		resource.setStates(states);
		
		if (LOG.isTraceEnabled()) {
			LOG.exit(resource);
		}
		return resource;
	}


}
