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
import com.github.kmbulebu.nicknack.server.restmodel.StateDefinition;
import com.github.kmbulebu.nicknack.server.restmodel.View;
import com.github.kmbulebu.nicknack.server.services.StateDefinitionsService;

@RestController
@RequestMapping(value="/api/stateDefinitions")
public class StateDefinitionsController {
	
	private static final Logger LOG = LogManager.getLogger(Application.APP_LOGGER_NAME);
	
	@Inject
	private StateDefinitionsService stateDefinitionsService;
	
	@JsonView(View.Summary.class)
	@RequestMapping(method={GET, HEAD})
	public List<StateDefinition> getAllStateDefinitions() {
		if (LOG.isTraceEnabled()) {
			LOG.entry();
		}
		
		final List<StateDefinition> stateDefinitions = stateDefinitionsService.getAllStateDefinitions();
		
		if (LOG.isTraceEnabled()) {
			LOG.exit(stateDefinitions);
		}
		return stateDefinitions;
	}
	
	@JsonView(View.Summary.class)
	@RequestMapping(method={GET, HEAD}, params="providerUuid")
	public List<StateDefinition> getStateDefinitionsByProvider(@RequestParam UUID providerUuid) {
		if (LOG.isTraceEnabled()) {
			LOG.entry();
		}
		
		final List<StateDefinition> stateDefinitions = stateDefinitionsService.getStateDefinitionsByProvider(providerUuid);
		
		if (LOG.isTraceEnabled()) {
			LOG.exit(stateDefinitions);
		}
		return stateDefinitions;
	}
	
	@RequestMapping(method={GET, HEAD}, value="/{uuid}")
	public StateDefinition getStateDefinition(@PathVariable UUID uuid) {
		if (LOG.isTraceEnabled()) {
			LOG.entry(uuid);
		}
		
		final StateDefinition stateDefinition = stateDefinitionsService.getStateDefinition(uuid); 
		
		
		if (LOG.isTraceEnabled()) {
			LOG.exit(stateDefinition);
		}
		return stateDefinition;
	}
	

}
