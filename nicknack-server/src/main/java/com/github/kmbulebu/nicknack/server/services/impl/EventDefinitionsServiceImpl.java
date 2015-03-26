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
import com.github.kmbulebu.nicknack.server.restmodel.EventDefinition;
import com.github.kmbulebu.nicknack.server.services.CoreProviderServiceWrapper;
import com.github.kmbulebu.nicknack.server.services.EventDefinitionMapper;
import com.github.kmbulebu.nicknack.server.services.EventDefinitionsService;

@Service
public class EventDefinitionsServiceImpl implements EventDefinitionsService {
	
	private static final Logger LOG = LogManager.getLogger(Application.APP_LOGGER_NAME);
	
	@Inject
	private CoreProviderServiceWrapper coreProviderService;
	
	@Inject
	private EventDefinitionMapper eventDefinitionMapper;

	@Override
	public List<EventDefinition> getAllEventDefinitions() {
		if (LOG.isTraceEnabled()) {
			LOG.entry();
		}
		
		final Collection<com.github.kmbulebu.nicknack.core.events.EventDefinition> coreEventDefinitions = coreProviderService.getNickNackProviderService().getEventDefinitions();
		
		final List<EventDefinition> eventDefinitions = new ArrayList<>(coreEventDefinitions.size());
		
		for (com.github.kmbulebu.nicknack.core.events.EventDefinition coreEventDefinition : coreEventDefinitions) {
			eventDefinitions.add(eventDefinitionMapper.map(coreEventDefinition));
		}
		
		if (LOG.isTraceEnabled()) {
			LOG.exit(eventDefinitions);
		}
		return eventDefinitions;
	}

	@Override
	public List<EventDefinition> getEventDefinitionsByProvider(UUID providerUuid) {
		if (LOG.isTraceEnabled()) {
			LOG.entry();
		}
		
		final Collection<com.github.kmbulebu.nicknack.core.events.EventDefinition> coreEventDefinitions = coreProviderService.getNickNackProviderService().getEventDefinitionsByProviderUuid(providerUuid);
		
		final List<EventDefinition> eventDefinitions = new ArrayList<>(coreEventDefinitions.size());
		
		for (com.github.kmbulebu.nicknack.core.events.EventDefinition coreEventDefinition : coreEventDefinitions) {
			eventDefinitions.add(eventDefinitionMapper.map(coreEventDefinition));
		}
		
		if (LOG.isTraceEnabled()) {
			LOG.exit(eventDefinitions);
		}
		return eventDefinitions;
	}

	@Override
	public EventDefinition getEventDefinition(UUID uuid) {
		if (LOG.isTraceEnabled()) {
			LOG.entry();
		}
		
		final com.github.kmbulebu.nicknack.core.events.EventDefinition coreEventDefinition = coreProviderService.getNickNackProviderService().getEventDefinition(uuid);
		
		EventDefinition eventDefinition = null;
		
		if (coreEventDefinition != null) {
			eventDefinition = eventDefinitionMapper.map(coreEventDefinition);
		}
		
		if (LOG.isTraceEnabled()) {
			LOG.exit(eventDefinition);
		}
		return eventDefinition;
	}

}
