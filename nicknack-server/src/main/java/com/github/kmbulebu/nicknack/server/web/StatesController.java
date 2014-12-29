package com.github.kmbulebu.nicknack.server.web;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.Collection;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.RelProvider;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.kmbulebu.nicknack.server.Application;
import com.github.kmbulebu.nicknack.server.model.StateDefinitionResource;
import com.github.kmbulebu.nicknack.server.model.StatesResource;
import com.github.kmbulebu.nicknack.server.services.StatesService;

@RestController
@RequestMapping(value="/api/states", produces={"application/hal+json"})
@ExposesResourceFor(StatesResource.class)
public class StatesController {
	
	private static final Logger LOG = LogManager.getLogger(Application.APP_LOGGER_NAME);
	
	@Autowired
	private StatesService statesService;
	
	@Autowired
	private RelProvider relProvider;
	
	@Autowired
	private EntityLinks entityLinks;
	
	// TODO Refactor HATEOAS link building. Time for ResourceAssembler or moving this stuff up the service. Although moving it up will create a circular dep.
	
	@RequestMapping(value="", method={RequestMethod.GET, RequestMethod.HEAD})
	public Resources<StatesResource> getAllStates() {
		if (LOG.isTraceEnabled()) {
			LOG.entry();
		}
		
		final Resources<StatesResource> resources = getAllStates(statesService.getAllStates(), null);
			
		if (LOG.isTraceEnabled()) {
			LOG.exit(resources);	
		}
		return resources;
	}
	
	@RequestMapping(value="", params="provider", method={RequestMethod.GET, RequestMethod.HEAD})
	public Resources<StatesResource> getAllStates(@RequestParam UUID provider) {
		if (LOG.isTraceEnabled()) {
			LOG.entry(provider);
		}
		
		final Resources<StatesResource> resources = getAllStates(statesService.getAllStatesByProvider(provider), provider);
			
		if (LOG.isTraceEnabled()) {
			LOG.exit(resources);	
		}
		return resources;
	}
	
	@RequestMapping(value="/{uuid}", method={RequestMethod.GET, RequestMethod.HEAD})
	public StatesResource getStates(@PathVariable UUID uuid) {
		if (LOG.isTraceEnabled()) {
			LOG.entry(uuid);
		}
		
		final StatesResource resource = statesService.getStates(uuid);
		
		resource.add(entityLinks.linkToSingleResource(StatesResource.class, uuid));
		resource.add(linkTo(methodOn(StateDefinitionsController.class).getStateDefinition(resource.getStateDefinitionUuid())).withRel(relProvider.getItemResourceRelFor(StateDefinitionResource.class)));
		
			
		if (LOG.isTraceEnabled()) {
			LOG.exit(resource);	
		}
		return resource;
	}
	
	
	private Resources<StatesResource> getAllStates(Collection<StatesResource> statesResources, UUID provider) {
		if (LOG.isTraceEnabled()) {
			LOG.entry(statesResources);
		}
		
		for (StatesResource resource : statesResources) {
			resource.add(entityLinks.linkToSingleResource(StatesResource.class, resource.getStateDefinitionUuid()).withRel(relProvider.getItemResourceRelFor(StatesResource.class)));
			resource.add(linkTo(methodOn(StateDefinitionsController.class).getStateDefinition(resource.getStateDefinitionUuid())).withRel(relProvider.getItemResourceRelFor(StateDefinitionResource.class)));
		}
		
		final Resources<StatesResource> resources = new Resources<>(statesResources);
		if (provider == null) {
			resources.add(entityLinks.linkToCollectionResource(StatesResource.class));
		} else {
			resources.add(linkTo(methodOn(StatesController.class).getAllStates(provider)).withSelfRel());
		}
		
		if (LOG.isTraceEnabled()) {
			LOG.exit(resources);	
		}
		return resources;
	}
	
	

}
