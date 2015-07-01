package com.github.kmbulebu.nicknack.server.controllers;

import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.kmbulebu.nicknack.server.Application;
import com.github.kmbulebu.nicknack.server.restmodel.Action;
import com.github.kmbulebu.nicknack.server.services.ActionQueueService;

@RestController
@RequestMapping(value="/api/actionQueue")
public class ActionQueueController {
	
	private static final Logger LOG = LogManager.getLogger(Application.APP_LOGGER_NAME);
	
	@Inject
	private ActionQueueService actionQueueService;
	
	@RequestMapping(method={POST})
	public void createAction(@RequestBody Action action) {
		if (LOG.isTraceEnabled()) {
			LOG.entry(action);
		}
		actionQueueService.enqueueAction(action);
		if (LOG.isTraceEnabled()) {
			LOG.exit();
		}
	}
	
	@RequestMapping(method={PUT}, value="/{id}")
	public void updateAction(@PathVariable Long id) {
		if (LOG.isTraceEnabled()) {
			LOG.entry(id);
		}
		
		actionQueueService.enqueueAction(id);

		if (LOG.isTraceEnabled()) {
			LOG.exit();
		}
	}

}
