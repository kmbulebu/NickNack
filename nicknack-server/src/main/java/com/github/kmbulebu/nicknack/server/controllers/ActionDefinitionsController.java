package com.github.kmbulebu.nicknack.server.controllers;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.HEAD;

import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.github.kmbulebu.nicknack.server.Application;
import com.github.kmbulebu.nicknack.server.restmodel.ActionDefinition;
import com.github.kmbulebu.nicknack.server.restmodel.View;
import com.github.kmbulebu.nicknack.server.services.ActionDefinitionsService;

@RestController
@RequestMapping(value="/api/actionDefinitions")
public class ActionDefinitionsController {
	
	private static final Logger LOG = LogManager.getLogger(Application.APP_LOGGER_NAME);
	
	@Inject
	private ActionDefinitionsService actionDefinitionsService;
	
	@JsonView(View.Summary.class)
	@RequestMapping(method={GET, HEAD})
	public List<ActionDefinition> getAllActionDefinitions() {
		if (LOG.isTraceEnabled()) {
			LOG.entry();
		}
		
		final List<ActionDefinition> actionDefinitions = actionDefinitionsService.getAllActionDefinitions();
		
		if (LOG.isTraceEnabled()) {
			LOG.exit(actionDefinitions);
		}
		return actionDefinitions;
	}
	
	@JsonView(View.Summary.class)
	@RequestMapping(method={GET, HEAD}, params="providerUuid")
	public List<ActionDefinition> getActionDefinitionsByProvider(@RequestParam UUID providerUuid) {
		if (LOG.isTraceEnabled()) {
			LOG.entry();
		}
		
		final List<ActionDefinition> actionDefinitions = actionDefinitionsService.getActionDefinitionsByProvider(providerUuid);
		
		if (LOG.isTraceEnabled()) {
			LOG.exit(actionDefinitions);
		}
		return actionDefinitions;
	}
	
	@RequestMapping(method={GET, HEAD}, value="/{uuid}")
	public ActionDefinition getActionDefinition(@PathVariable UUID uuid) {
		if (LOG.isTraceEnabled()) {
			LOG.entry(uuid);
		}
		
		final ActionDefinition actionDefinition = actionDefinitionsService.getActionDefinition(uuid); 
		
		
		if (LOG.isTraceEnabled()) {
			LOG.exit(actionDefinition);
		}
		return actionDefinition;
	}
	

}
