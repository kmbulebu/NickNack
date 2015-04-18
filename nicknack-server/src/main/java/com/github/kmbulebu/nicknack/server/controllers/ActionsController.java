package com.github.kmbulebu.nicknack.server.controllers;

import static org.springframework.web.bind.annotation.RequestMethod.PUT;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.HEAD;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;

import java.util.List;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.github.kmbulebu.nicknack.server.Application;
import com.github.kmbulebu.nicknack.server.restmodel.Action;
import com.github.kmbulebu.nicknack.server.services.ActionsService;

@RestController
@RequestMapping(value="/api/actions")
public class ActionsController {
	
	private static final Logger LOG = LogManager.getLogger(Application.APP_LOGGER_NAME);
	
	@Inject
	private ActionsService actionsService; 
	
	@ResponseStatus(HttpStatus.CREATED)
	@RequestMapping(method={POST})
	public void createAction(@RequestBody Action action) {
		if (LOG.isTraceEnabled()) {
			LOG.entry(action);
		}
		actionsService.saveAction(action);
		if (LOG.isTraceEnabled()) {
			LOG.exit();
		}
	}
	
	@RequestMapping(method={PUT, POST}, value="/{id}")
	public ResponseEntity<Void> updateAction(@PathVariable Long id, @RequestBody Action action) {
		if (LOG.isTraceEnabled()) {
			LOG.entry(id, action);
		}
		
		if (!id.equals(action.getId())) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		actionsService.saveAction(action);
		
		final ResponseEntity<Void> result = new ResponseEntity<>(HttpStatus.OK);
		if (LOG.isTraceEnabled()) {
			LOG.exit(result);
		}
		return result;
	}
	
	@RequestMapping(method={GET, HEAD}, value="/{id}")
	public Action getAction(@PathVariable Long id) {
		if (LOG.isTraceEnabled()) {
			LOG.entry(id);
		}
		final Action action = actionsService.getAction(id);
		if (LOG.isTraceEnabled()) {
			LOG.exit(action);
		}
		return action;
	}
	
	@RequestMapping(method={DELETE}, value="/{id}")
	public void deleteAction(@PathVariable Long id) {
		if (LOG.isTraceEnabled()) {
			LOG.entry(id);
		}
		actionsService.deleteAction(id);
		if (LOG.isTraceEnabled()) {
			LOG.exit();
		}
	}
	
	@RequestMapping(method={GET, HEAD})
	public List<Action> getAllActions() {
		if (LOG.isTraceEnabled()) {
			LOG.entry();
		}
		final List<Action> actions = actionsService.getAllActions();
		
		if (LOG.isTraceEnabled()) {
			LOG.exit(actions);
		}
		return actions;
	}
	
	

}
