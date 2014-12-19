package com.github.kmbulebu.nicknack.server.web;

import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.github.kmbulebu.nicknack.server.Application;
import com.github.kmbulebu.nicknack.server.model.ActionResource;
import com.github.kmbulebu.nicknack.server.services.ActionQueueService;

@RestController
@RequestMapping(value="/api/actionQueue", produces={"application/hal+json"})
public class ActionQueueController {
	
	private static final Logger LOG = LogManager.getLogger(Application.APP_LOGGER_NAME);
	
	@Autowired
	private ActionQueueService actionQueueService;
	
	@RequestMapping(value="", method={RequestMethod.POST}, consumes={MediaType.APPLICATION_JSON_VALUE})
	@ResponseStatus(HttpStatus.CREATED)
	public void createAction(@RequestBody ActionResource action) {
		if (LOG.isTraceEnabled()) {
			LOG.entry(action);
		}
		
		actionQueueService.enqueueAction(action);
		
		if (LOG.isTraceEnabled()) {
			LOG.exit();
		}
	}
	
	@RequestMapping(value="/{uuid}", method={RequestMethod.PUT}, consumes={MediaType.APPLICATION_JSON_VALUE})
	@ResponseStatus(HttpStatus.OK)
	public void createAction(@PathVariable UUID uuid) {
		if (LOG.isTraceEnabled()) {
			LOG.entry(uuid);
		}
		
		actionQueueService.enqueueAction(uuid);
		
		if (LOG.isTraceEnabled()) {
			LOG.exit();
		}
	}

}
