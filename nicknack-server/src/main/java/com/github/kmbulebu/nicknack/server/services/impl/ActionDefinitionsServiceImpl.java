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
import com.github.kmbulebu.nicknack.server.restmodel.ActionDefinition;
import com.github.kmbulebu.nicknack.server.services.CoreProviderServiceWrapper;
import com.github.kmbulebu.nicknack.server.services.ActionDefinitionMapper;
import com.github.kmbulebu.nicknack.server.services.ActionDefinitionsService;

@Service
public class ActionDefinitionsServiceImpl implements ActionDefinitionsService {
	
	private static final Logger LOG = LogManager.getLogger(Application.APP_LOGGER_NAME);
	
	@Inject
	private CoreProviderServiceWrapper coreProviderService;
	
	@Inject
	private ActionDefinitionMapper actionDefinitionMapper;

	@Override
	public List<ActionDefinition> getAllActionDefinitions() {
		if (LOG.isTraceEnabled()) {
			LOG.entry();
		}
		
		final Collection<com.github.kmbulebu.nicknack.core.actions.ActionDefinition> coreActionDefinitions = coreProviderService.getNickNackProviderService().getActionDefinitions();
		
		final List<ActionDefinition> actionDefinitions = new ArrayList<>(coreActionDefinitions.size());
		
		for (com.github.kmbulebu.nicknack.core.actions.ActionDefinition coreActionDefinition : coreActionDefinitions) {
			actionDefinitions.add(actionDefinitionMapper.map(coreActionDefinition));
		}
		
		if (LOG.isTraceEnabled()) {
			LOG.exit(actionDefinitions);
		}
		return actionDefinitions;
	}

	@Override
	public List<ActionDefinition> getActionDefinitionsByProvider(UUID providerUuid) {
		if (LOG.isTraceEnabled()) {
			LOG.entry();
		}
		
		final Collection<com.github.kmbulebu.nicknack.core.actions.ActionDefinition> coreActionDefinitions = coreProviderService.getNickNackProviderService().getActionDefinitionsByProviderUuid(providerUuid);
		
		final List<ActionDefinition> actionDefinitions = new ArrayList<>(coreActionDefinitions.size());
		
		for (com.github.kmbulebu.nicknack.core.actions.ActionDefinition coreActionDefinition : coreActionDefinitions) {
			actionDefinitions.add(actionDefinitionMapper.map(coreActionDefinition));
		}
		
		if (LOG.isTraceEnabled()) {
			LOG.exit(actionDefinitions);
		}
		return actionDefinitions;
	}

	@Override
	public ActionDefinition getActionDefinition(UUID uuid) {
		if (LOG.isTraceEnabled()) {
			LOG.entry();
		}
		
		final com.github.kmbulebu.nicknack.core.actions.ActionDefinition coreActionDefinition = coreProviderService.getNickNackProviderService().getActionDefinition(uuid);
		
		ActionDefinition actionDefinition = null;
		
		if (coreActionDefinition != null) {
			actionDefinition = actionDefinitionMapper.map(coreActionDefinition);
		}
		
		if (LOG.isTraceEnabled()) {
			LOG.exit(actionDefinition);
		}
		return actionDefinition;
	}

}
