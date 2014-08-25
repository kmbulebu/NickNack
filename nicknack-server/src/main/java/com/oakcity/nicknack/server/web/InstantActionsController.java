package com.oakcity.nicknack.server.web;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.oakcity.nicknack.server.Application;
import com.oakcity.nicknack.server.model.ActionResource;
import com.oakcity.nicknack.server.services.ActionRunnerService;

@RestController
@RequestMapping(value="/api/instantactions", produces={"application/hal+json"})
@ExposesResourceFor(ActionResource.class)
public class InstantActionsController {
	
	private static final Logger LOG = LogManager.getLogger(Application.APP_LOGGER_NAME);
	
	@Autowired
	private ActionRunnerService actionRunnerService;
	
	@RequestMapping(value="", method={RequestMethod.POST}, consumes={MediaType.APPLICATION_JSON_VALUE})
	@ResponseStatus(HttpStatus.CREATED)
	public void createAction(@RequestBody ActionResource action) {
		if (LOG.isTraceEnabled()) {
			LOG.entry(action);
		}
		
		actionRunnerService.runActionNow(action);
		
		if (LOG.isTraceEnabled()) {
			LOG.exit();
		}
	}

}
