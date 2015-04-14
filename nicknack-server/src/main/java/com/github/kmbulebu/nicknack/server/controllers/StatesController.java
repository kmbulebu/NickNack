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
import com.github.kmbulebu.nicknack.server.restmodel.State;
import com.github.kmbulebu.nicknack.server.restmodel.View;
import com.github.kmbulebu.nicknack.server.services.StatesService;

@RestController
@RequestMapping(value="/api/states")
public class StatesController {
	
	private static final Logger LOG = LogManager.getLogger(Application.APP_LOGGER_NAME);
	
	@Inject
	private StatesService statesService;
	
	@JsonView(View.Summary.class)
	@RequestMapping(method={GET, HEAD})
	public List<State> getStates() {
		if (LOG.isTraceEnabled()) {
			LOG.entry();
		}
		
		final List<State> states = statesService.getAllStates();
		
		if (LOG.isTraceEnabled()) {
			LOG.exit(states);
		}
		return states;
	}
	
	@JsonView(View.Summary.class)
	@RequestMapping(method={GET, HEAD}, params="providerUuid")
	public List<State> getStatesByProvider(@RequestParam UUID providerUuid) {
		if (LOG.isTraceEnabled()) {
			LOG.entry();
		}
		
		final List<State> states = statesService.getStatesByProvider(providerUuid);
		
		if (LOG.isTraceEnabled()) {
			LOG.exit(states);
		}
		return states;
	}
	
	@RequestMapping(method={GET, HEAD}, value="/{uuid}")
	public List<State> getStates(@PathVariable UUID uuid) {
		if (LOG.isTraceEnabled()) {
			LOG.entry(uuid);
		}
		
		final List<State> states = statesService.getStatesByStateDefinition(uuid);
		
		if (LOG.isTraceEnabled()) {
			LOG.exit(states);
		}
		return states;
	}
	

}
