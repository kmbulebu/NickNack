package com.oakcity.nicknack.server.web;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.List;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.RelProvider;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.oakcity.nicknack.server.AppConfiguration;
import com.oakcity.nicknack.server.model.ActionResource;
import com.oakcity.nicknack.server.services.ActionsService;

@RestController
@RequestMapping(value="/api/plans/{planUuid}/actions", produces={"application/hal+json"})
@ExposesResourceFor(ActionResource.class)
public class ActionsController {
	
	private static final Logger LOG = LogManager.getLogger(AppConfiguration.APP_LOGGER_NAME);
	
	@Autowired
	private ActionsService actionsService;
	
	@Autowired
	private RelProvider relProvider;
	
	@Autowired
	private EntityLinks entityLinks;
	
	@RequestMapping(value="", method={RequestMethod.GET, RequestMethod.HEAD})
	public Resources<ActionResource> getActions(@PathVariable UUID planUuid) {
		if (LOG.isTraceEnabled()) {
			LOG.entry();
		}
		
		final List<ActionResource> actionResources = actionsService.getActions(planUuid);
				
		final Resources<ActionResource> resources = new Resources<ActionResource>(actionResources);
		
		// Add links
		addLinks(planUuid, resources);
		
		if (LOG.isTraceEnabled()) {
			LOG.exit(resources);
		}
		return resources;
	}
	
	@RequestMapping(value="", method={RequestMethod.POST}, consumes={MediaType.APPLICATION_JSON_VALUE})
	@ResponseStatus(HttpStatus.CREATED)
	public ActionResource createAction(@PathVariable UUID planUuid, @RequestBody ActionResource newAction) {
		if (LOG.isTraceEnabled()) {
			LOG.entry(newAction);
		}
		
		final ActionResource resource = actionsService.createAction(planUuid, newAction);
		
		addLinks(planUuid, resource);
		
		if (LOG.isTraceEnabled()) {
			LOG.exit(resource);
		}
		return resource;
	}
	
	@RequestMapping(value="/{uuid}", method={RequestMethod.GET, RequestMethod.HEAD})
	public ActionResource getAction(@PathVariable UUID planUuid, @PathVariable UUID uuid) {
		if (LOG.isTraceEnabled()) {
			LOG.entry(uuid);
		}
		
		final ActionResource resource = actionsService.getAction(uuid);
		
		addLinks(planUuid, resource);
		
		if (LOG.isTraceEnabled()) {
			LOG.exit(resource);
		}
		return resource;
	}
	
	@RequestMapping(value="/{uuid}", method={RequestMethod.DELETE})
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteAction(@PathVariable UUID planUuid, @PathVariable UUID uuid) {
		if (LOG.isTraceEnabled()) {
			LOG.entry(uuid);
		}
		
		actionsService.deleteAction(uuid);
		
		if (LOG.isTraceEnabled()) {
			LOG.exit();
		}
	}
	
	private void addLinks(UUID planUuid, ActionResource resource) {
		resource.add(linkTo(methodOn(ActionsController.class).getAction(planUuid, resource.getUuid())).withSelfRel());
	}
	
	private void addLinks(UUID planUuid, Resources<ActionResource> resources) {
		resources.add(linkTo(methodOn(ActionsController.class).getActions(planUuid)).withSelfRel());
		
		for (ActionResource resource : resources.getContent()) {
			addLinks(planUuid, resource);
		}
	}

}
