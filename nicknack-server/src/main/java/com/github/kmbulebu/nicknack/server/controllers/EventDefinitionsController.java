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
import com.github.kmbulebu.nicknack.server.restmodel.EventDefinition;
import com.github.kmbulebu.nicknack.server.restmodel.View;
import com.github.kmbulebu.nicknack.server.services.EventDefinitionsService;

@RestController
@RequestMapping(value="/api/eventDefinitions")
public class EventDefinitionsController {
	
	private static final Logger LOG = LogManager.getLogger(Application.APP_LOGGER_NAME);
	
	@Inject
	private EventDefinitionsService eventDefinitionsService;
	
	@JsonView(View.Summary.class)
	@RequestMapping(method={GET, HEAD})
	public List<EventDefinition> getAllEventDefinitions() {
		if (LOG.isTraceEnabled()) {
			LOG.entry();
		}
		
		final List<EventDefinition> eventDefinitions = eventDefinitionsService.getAllEventDefinitions();
		
		if (LOG.isTraceEnabled()) {
			LOG.exit(eventDefinitions);
		}
		return eventDefinitions;
	}
	
	@JsonView(View.Summary.class)
	@RequestMapping(method={GET, HEAD}, params="providerUuid")
	public List<EventDefinition> getEventDefinitionsByProvider(@RequestParam UUID providerUuid) {
		if (LOG.isTraceEnabled()) {
			LOG.entry();
		}
		
		final List<EventDefinition> eventDefinitions = eventDefinitionsService.getEventDefinitionsByProvider(providerUuid);
		
		if (LOG.isTraceEnabled()) {
			LOG.exit(eventDefinitions);
		}
		return eventDefinitions;
	}
	
	@RequestMapping(method={GET, HEAD}, value="/{uuid}")
	public EventDefinition getEventDefinition(@PathVariable UUID uuid) {
		if (LOG.isTraceEnabled()) {
			LOG.entry(uuid);
		}
		
		final EventDefinition eventDefinition = eventDefinitionsService.getEventDefinition(uuid); 
		
		
		if (LOG.isTraceEnabled()) {
			LOG.exit(eventDefinition);
		}
		return eventDefinition;
	}
	

}
