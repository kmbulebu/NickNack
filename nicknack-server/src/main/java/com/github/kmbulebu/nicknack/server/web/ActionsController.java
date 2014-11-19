package com.github.kmbulebu.nicknack.server.web;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.List;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.github.kmbulebu.nicknack.server.Application;
import com.github.kmbulebu.nicknack.server.model.ActionResource;
import com.github.kmbulebu.nicknack.server.services.ActionsService;

@RestController
@RequestMapping(value="/api/actions", produces={"application/hal+json"})
@ExposesResourceFor(ActionResource.class)
public class ActionsController {
	
	private static final Logger LOG = LogManager.getLogger(Application.APP_LOGGER_NAME);
	
	@Autowired
	private ActionsService actionsService;
	
	@RequestMapping(value = "/{uuid}", method={RequestMethod.GET, RequestMethod.HEAD})
	@ResponseStatus(HttpStatus.OK)
	public ActionResource getAction(@PathVariable UUID uuid) {
		if (LOG.isTraceEnabled()) {
			LOG.entry();
		}
		
		final ActionResource resource = actionsService.getAction(uuid);
		
		if (LOG.isTraceEnabled()) {
			LOG.exit(resource);	
		}
		return resource;
	}
	
	@RequestMapping(method={RequestMethod.GET, RequestMethod.HEAD})
	@ResponseStatus(HttpStatus.OK)
	public Resources<ActionResource> getActions() {
		if (LOG.isTraceEnabled()) {
			LOG.entry();
		}
		
		final List<ActionResource> actionResources = actionsService.getActions();
		
		final Resources<ActionResource> resources = new Resources<ActionResource>(actionResources);
		
		// Add links
		addLinks(resources);
		
		if (LOG.isTraceEnabled()) {
			LOG.exit(resources);	
		}
		return resources;
	}
	
	@RequestMapping(method={RequestMethod.GET, RequestMethod.HEAD}, params="planUuid")
	@ResponseStatus(HttpStatus.OK)
	public Resources<ActionResource> getActionsByPlan(@RequestParam UUID planUuid) {
		if (LOG.isTraceEnabled()) {
			LOG.entry();
		}
		
		final List<ActionResource> actionResources = actionsService.getActionsByPlan(planUuid);
		
		final Resources<ActionResource> resources = new Resources<ActionResource>(actionResources);
		
		// Add links
		addLinks(resources);
		
		if (LOG.isTraceEnabled()) {
			LOG.exit(resources);	
		}
		return resources;
	}
	
	@RequestMapping(method={RequestMethod.POST}, params="planUuid", consumes={MediaType.APPLICATION_JSON_VALUE})
	@ResponseStatus(HttpStatus.CREATED)
	public ActionResource createActionForPlan(@RequestBody ActionResource action, @RequestParam UUID planUuid) {
		if (LOG.isTraceEnabled()) {
			LOG.entry(action);
		}
		
		final ActionResource createdResource = actionsService.createAction(action);
		
		actionsService.addActionToPlan(planUuid, createdResource.getUuid());
		
		if (LOG.isTraceEnabled()) {
			LOG.exit(createdResource);
		}
		return createdResource;
	}
	
	@RequestMapping(method={RequestMethod.POST}, consumes={MediaType.APPLICATION_JSON_VALUE})
	@ResponseStatus(HttpStatus.CREATED)
	public ActionResource createAction(@RequestBody ActionResource action) {
		if (LOG.isTraceEnabled()) {
			LOG.entry(action);
		}
		
		final ActionResource createdResource = actionsService.createAction(action);
		
		if (LOG.isTraceEnabled()) {
			LOG.exit(createdResource);
		}
		return createdResource;
	}
	
	@RequestMapping(value = "/{uuid}", method={RequestMethod.PUT}, consumes={MediaType.APPLICATION_JSON_VALUE})
	@ResponseStatus(HttpStatus.OK)
	public ActionResource modifyAction(@PathVariable UUID uuid, @RequestBody ActionResource action) {
		if (LOG.isTraceEnabled()) {
			LOG.entry(action);
		}
		
		final ActionResource modifiedResource = actionsService.modifyAction(action);
		
		if (LOG.isTraceEnabled()) {
			LOG.exit(modifiedResource);
		}
		return modifiedResource;
	}
	
	@RequestMapping(value = "/{uuid}", method={RequestMethod.DELETE})
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteAction(@PathVariable UUID uuid) {
		if (LOG.isTraceEnabled()) {
			LOG.entry(uuid);
		}
		
		actionsService.deleteAction(uuid);
		
		if (LOG.isTraceEnabled()) {
			LOG.exit();
		}
	}
	
	private void addLinks(ActionResource resource) {
		resource.add(linkTo(methodOn(ActionsController.class).getAction(resource.getUuid())).withSelfRel());
	}
	
	private void addLinks(Resources<ActionResource> resources) {
		resources.add(linkTo(methodOn(ActionsController.class).getActions()).withSelfRel());
		
		for (ActionResource resource : resources.getContent()) {
			addLinks(resource);
		}
	}
}
