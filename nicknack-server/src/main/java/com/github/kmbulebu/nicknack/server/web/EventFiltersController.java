package com.github.kmbulebu.nicknack.server.web;

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

import com.github.kmbulebu.nicknack.server.Application;
import com.github.kmbulebu.nicknack.server.model.EventFilterResource;
import com.github.kmbulebu.nicknack.server.services.EventFiltersService;

@RestController
@RequestMapping(value="/api/plans/{planUuid}/eventFilters", produces={"application/hal+json"})
@ExposesResourceFor(EventFilterResource.class)
public class EventFiltersController {
	
	private static final Logger LOG = LogManager.getLogger(Application.APP_LOGGER_NAME);
	
	@Autowired
	private EventFiltersService eventFiltersService;
	
	@Autowired
	private RelProvider relProvider;
	
	@Autowired
	private EntityLinks entityLinks;
	
	@RequestMapping(value="", method={RequestMethod.GET, RequestMethod.HEAD})
	public Resources<EventFilterResource> getEventFilters(@PathVariable UUID planUuid) {
		if (LOG.isTraceEnabled()) {
			LOG.entry();
		}
		
		final List<EventFilterResource> eventFilterResources = eventFiltersService.getEventFilters(planUuid);
				
		final Resources<EventFilterResource> resources = new Resources<EventFilterResource>(eventFilterResources);
		
		// Add links
		addLinks(planUuid, resources);
		
		if (LOG.isTraceEnabled()) {
			LOG.exit(resources);
		}
		return resources;
	}
	
	@RequestMapping(value="", method={RequestMethod.POST}, consumes={MediaType.APPLICATION_JSON_VALUE})
	@ResponseStatus(HttpStatus.CREATED)
	public EventFilterResource createEventFilter(@PathVariable UUID planUuid, @RequestBody EventFilterResource newEventFilter) {
		if (LOG.isTraceEnabled()) {
			LOG.entry(newEventFilter);
		}
		
		final EventFilterResource resource = eventFiltersService.createEventFilter(planUuid, newEventFilter);
		
		addLinks(planUuid, resource);
		
		if (LOG.isTraceEnabled()) {
			LOG.exit(resource);
		}
		return resource;
	}
	
	@RequestMapping(value="/{uuid}", method={RequestMethod.PUT}, consumes={MediaType.APPLICATION_JSON_VALUE})
	@ResponseStatus(HttpStatus.OK)
	public EventFilterResource modifyEventFilter(@PathVariable UUID planUuid, @PathVariable UUID uuid, @RequestBody EventFilterResource eventFilter) {
		if (LOG.isTraceEnabled()) {
			LOG.entry(eventFilter);
		}
		
		if (!uuid.equals(eventFilter.getUuid())) {
			throw new IllegalArgumentException("Event Filter uuid did not match path.");
		}
		
		final EventFilterResource resource = eventFiltersService.modifyEventFilter(eventFilter);
		
		addLinks(planUuid, resource);
		
		if (LOG.isTraceEnabled()) {
			LOG.exit(resource);
		}
		return resource;
	}
	
	@RequestMapping(value="/{uuid}", method={RequestMethod.GET, RequestMethod.HEAD})
	public EventFilterResource getEventFilter(@PathVariable UUID planUuid, @PathVariable UUID uuid) {
		if (LOG.isTraceEnabled()) {
			LOG.entry(uuid);
		}
		
		final EventFilterResource resource = eventFiltersService.getEventFilter(uuid);
		
		addLinks(planUuid, resource);
		
		if (LOG.isTraceEnabled()) {
			LOG.exit(resource);
		}
		return resource;
	}
	
	@RequestMapping(value="/{uuid}", method={RequestMethod.DELETE})
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteEventFilter(@PathVariable UUID planUuid, @PathVariable UUID uuid) {
		if (LOG.isTraceEnabled()) {
			LOG.entry(uuid);
		}
		
		eventFiltersService.deleteEventFilter(uuid);
		
		if (LOG.isTraceEnabled()) {
			LOG.exit();
		}
	}
	
	private void addLinks(UUID planUuid, EventFilterResource resource) {
		resource.add(linkTo(methodOn(EventFiltersController.class).getEventFilter(planUuid, resource.getUuid())).withSelfRel());
	}
	
	private void addLinks(UUID planUuid, Resources<EventFilterResource> resources) {
		resources.add(linkTo(methodOn(EventFiltersController.class).getEventFilters(planUuid)).withSelfRel());
		
		for (EventFilterResource resource : resources.getContent()) {
			addLinks(planUuid, resource);
		}
	}

}
