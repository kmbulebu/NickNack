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
import com.github.kmbulebu.nicknack.server.model.StateFilterResource;
import com.github.kmbulebu.nicknack.server.services.StateFiltersService;

@RestController
@RequestMapping(value="/api/plans/{planUuid}/stateFilters", produces={"application/hal+json"})
@ExposesResourceFor(StateFilterResource.class)
public class PlansStateFiltersController {
	
	private static final Logger LOG = LogManager.getLogger(Application.APP_LOGGER_NAME);
	
	@Autowired
	private StateFiltersService stateFiltersService;
	
	@Autowired
	private RelProvider relProvider;
	
	@Autowired
	private EntityLinks entityLinks;
	
	@RequestMapping(value="", method={RequestMethod.GET, RequestMethod.HEAD})
	public Resources<StateFilterResource> getStateFilters(@PathVariable UUID planUuid) {
		if (LOG.isTraceEnabled()) {
			LOG.entry();
		}
		
		final List<StateFilterResource> stateFilterResources = stateFiltersService.getStateFilters(planUuid);
				
		final Resources<StateFilterResource> resources = new Resources<StateFilterResource>(stateFilterResources);
		
		// Add links
		addLinks(planUuid, resources);
		
		if (LOG.isTraceEnabled()) {
			LOG.exit(resources);
		}
		return resources;
	}
	
	@RequestMapping(value="", method={RequestMethod.POST}, consumes={MediaType.APPLICATION_JSON_VALUE})
	@ResponseStatus(HttpStatus.CREATED)
	public StateFilterResource createStateFilter(@PathVariable UUID planUuid, @RequestBody StateFilterResource newStateFilter) {
		if (LOG.isTraceEnabled()) {
			LOG.entry(newStateFilter);
		}
		
		final StateFilterResource resource = stateFiltersService.createStateFilter(planUuid, newStateFilter);
		
		addLinks(planUuid, resource);
		
		if (LOG.isTraceEnabled()) {
			LOG.exit(resource);
		}
		return resource;
	}
	
	@RequestMapping(value="/{uuid}", method={RequestMethod.PUT}, consumes={MediaType.APPLICATION_JSON_VALUE})
	@ResponseStatus(HttpStatus.OK)
	public StateFilterResource modifyStateFilter(@PathVariable UUID planUuid, @PathVariable UUID uuid, @RequestBody StateFilterResource stateFilter) {
		if (LOG.isTraceEnabled()) {
			LOG.entry(stateFilter);
		}
		
		if (!uuid.equals(stateFilter.getUuid())) {
			throw new IllegalArgumentException("State Filter uuid did not match path.");
		}
		
		final StateFilterResource resource = stateFiltersService.modifyStateFilter(stateFilter);
		
		addLinks(planUuid, resource);
		
		if (LOG.isTraceEnabled()) {
			LOG.exit(resource);
		}
		return resource;
	}
	
	@RequestMapping(value="/{uuid}", method={RequestMethod.GET, RequestMethod.HEAD})
	public StateFilterResource getStateFilter(@PathVariable UUID planUuid, @PathVariable UUID uuid) {
		if (LOG.isTraceEnabled()) {
			LOG.entry(uuid);
		}
		
		final StateFilterResource resource = stateFiltersService.getStateFilter(uuid);
		
		addLinks(planUuid, resource);
		
		if (LOG.isTraceEnabled()) {
			LOG.exit(resource);
		}
		return resource;
	}
	
	@RequestMapping(value="/{uuid}", method={RequestMethod.DELETE})
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteStateFilter(@PathVariable UUID planUuid, @PathVariable UUID uuid) {
		if (LOG.isTraceEnabled()) {
			LOG.entry(uuid);
		}
		
		stateFiltersService.deleteStateFilter(uuid);
		
		if (LOG.isTraceEnabled()) {
			LOG.exit();
		}
	}
	
	private void addLinks(UUID planUuid, StateFilterResource resource) {
		resource.add(linkTo(methodOn(PlansStateFiltersController.class).getStateFilter(planUuid, resource.getUuid())).withSelfRel());
	}
	
	private void addLinks(UUID planUuid, Resources<StateFilterResource> resources) {
		resources.add(linkTo(methodOn(PlansStateFiltersController.class).getStateFilters(planUuid)).withSelfRel());
		
		for (StateFilterResource resource : resources.getContent()) {
			addLinks(planUuid, resource);
		}
	}

}
