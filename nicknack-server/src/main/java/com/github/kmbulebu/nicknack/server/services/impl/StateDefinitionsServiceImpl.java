package com.github.kmbulebu.nicknack.server.services.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.github.kmbulebu.nicknack.server.Application;
import com.github.kmbulebu.nicknack.server.restmodel.StateDefinition;
import com.github.kmbulebu.nicknack.server.services.CoreProviderServiceWrapper;
import com.github.kmbulebu.nicknack.server.services.StateDefinitionMapper;
import com.github.kmbulebu.nicknack.server.services.StateDefinitionsService;

@Service
public class StateDefinitionsServiceImpl implements StateDefinitionsService {
	
	private static final Logger LOG = LogManager.getLogger(Application.APP_LOGGER_NAME);
	
	@Inject
	private CoreProviderServiceWrapper coreProviderService;
	
	@Inject
	private StateDefinitionMapper stateDefinitionMapper;

	@Override
	public List<StateDefinition> getAllStateDefinitions() {
		if (LOG.isTraceEnabled()) {
			LOG.entry();
		}
		
		final Collection<com.github.kmbulebu.nicknack.core.states.StateDefinition> coreStateDefinitions = coreProviderService.getNickNackProviderService().getStateDefinitions();
		
		final List<StateDefinition> stateDefinitions = new ArrayList<>(coreStateDefinitions.size());
		
		for (com.github.kmbulebu.nicknack.core.states.StateDefinition coreStateDefinition : coreStateDefinitions) {
			final UUID providerUuid = coreProviderService.getNickNackProviderService().getProviderByStateDefinitionUuid(coreStateDefinition.getUUID()).getUuid();
			stateDefinitions.add(stateDefinitionMapper.map(coreStateDefinition, providerUuid));
		}
		
		if (LOG.isTraceEnabled()) {
			LOG.exit(stateDefinitions);
		}
		return stateDefinitions;
	}

	@Override
	public List<StateDefinition> getStateDefinitionsByProvider(UUID providerUuid) {
		if (LOG.isTraceEnabled()) {
			LOG.entry();
		}
		
		final Collection<com.github.kmbulebu.nicknack.core.states.StateDefinition> coreStateDefinitions = coreProviderService.getNickNackProviderService().getStateDefinitionsByProviderUuid(providerUuid);
		
		final List<StateDefinition> stateDefinitions = new ArrayList<>(coreStateDefinitions.size());
		
		for (com.github.kmbulebu.nicknack.core.states.StateDefinition coreStateDefinition : coreStateDefinitions) {
			stateDefinitions.add(stateDefinitionMapper.map(coreStateDefinition, providerUuid));
		}
		
		if (LOG.isTraceEnabled()) {
			LOG.exit(stateDefinitions);
		}
		return stateDefinitions;
	}

	@Override
	public StateDefinition getStateDefinition(UUID uuid) {
		if (LOG.isTraceEnabled()) {
			LOG.entry();
		}
		
		final com.github.kmbulebu.nicknack.core.states.StateDefinition coreStateDefinition = coreProviderService.getNickNackProviderService().getStateDefinition(uuid);
		
		StateDefinition stateDefinition = null;
		
		if (coreStateDefinition != null) {
			final UUID providerUuid = coreProviderService.getNickNackProviderService().getProviderByStateDefinitionUuid(coreStateDefinition.getUUID()).getUuid();
			stateDefinition = stateDefinitionMapper.map(coreStateDefinition, providerUuid);
		}
		
		if (LOG.isTraceEnabled()) {
			LOG.exit(stateDefinition);
		}
		return stateDefinition;
	}

}
