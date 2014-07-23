package com.oakcity.nicknack.server.services.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oakcity.nicknack.core.actions.ActionDefinition;
import com.oakcity.nicknack.core.actions.ParameterDefinition;
import com.oakcity.nicknack.core.providers.ProviderService;
import com.oakcity.nicknack.server.AppConfiguration;
import com.oakcity.nicknack.server.services.ActionDefinitionService;


@Service
public class ActionDefinitionServiceImpl implements ActionDefinitionService {
	
	private static final Logger LOG = LogManager.getLogger(AppConfiguration.APP_LOGGER_NAME);
	
	@Autowired
	private ProviderService providerService;


	@Override
	public List<ActionDefinition> getActionDefinitions() {
		if (LOG.isTraceEnabled()) {
			LOG.entry();
		}
		
		final Collection<ActionDefinition> set = providerService.getActionDefinitions().values();
		
		// TODO Add exceptions for not found, etc.
		
		final List<ActionDefinition> actionDefinitions = new ArrayList<ActionDefinition>(set);
		
		if (LOG.isTraceEnabled()) {
			LOG.exit(actionDefinitions);
		}
		return actionDefinitions;
		
	}

	@Override
	public ActionDefinition getActionDefinition(final UUID uuid) {
		if (LOG.isTraceEnabled()) {
			LOG.entry(uuid);
		}
		
		final ActionDefinition actionDefinition = providerService.getActionDefinitions().get(uuid);
		
		// TODO Add exceptions for not found, etc.
		
		if (LOG.isTraceEnabled()) {
			LOG.exit(actionDefinition);
		}
		return actionDefinition;
	}
	
	@Override
	public List<ParameterDefinition> getParameterDefinitions(final UUID actionUUID) {
		if (LOG.isTraceEnabled()) {
			LOG.entry(actionUUID);
		}
		
		final ActionDefinition actionDefinition = getActionDefinition(actionUUID);
				
		// TODO Add exceptions for not found, etc.
		
		final List<ParameterDefinition> parameterDefinitions = Collections.unmodifiableList(actionDefinition.getParameterDefinitions());
		
		if (LOG.isTraceEnabled()) {
			LOG.exit(parameterDefinitions);
		}
		return parameterDefinitions;
	}
	
	@Override
	public ParameterDefinition getParameterDefinition(final UUID actionUUID, final UUID uuid) {
		if (LOG.isTraceEnabled()) {
			LOG.entry(actionUUID, uuid);
		}
		
		final List<ParameterDefinition> parameterDefinitions = getParameterDefinitions(actionUUID);
		
		ParameterDefinition parameterDefinition = null;
		
		for (ParameterDefinition anParameterDefinition : parameterDefinitions) {
			if (uuid.equals(anParameterDefinition.getUUID())) {
				parameterDefinition = anParameterDefinition;
				break;
			}
		}
		
		if (LOG.isTraceEnabled()) {
			LOG.exit(parameterDefinition);
		}
		return parameterDefinition;
	}

}
