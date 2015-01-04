package com.github.kmbulebu.nicknack.server.web;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.RelProvider;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.kmbulebu.nicknack.core.attributes.AttributeDefinition;
import com.github.kmbulebu.nicknack.core.states.StateDefinition;
import com.github.kmbulebu.nicknack.server.Application;
import com.github.kmbulebu.nicknack.server.model.AttributeDefinitionResource;
import com.github.kmbulebu.nicknack.server.model.StateDefinitionResource;
import com.github.kmbulebu.nicknack.server.services.StateDefinitionService;
import com.github.kmbulebu.nicknack.server.services.exceptions.AttributeDefinitionNotFoundException;
import com.github.kmbulebu.nicknack.server.services.exceptions.ProviderNotFoundException;
import com.github.kmbulebu.nicknack.server.services.exceptions.StateDefinitionNotFoundException;

@RestController
@RequestMapping(value="/api/stateDefinitions", produces={"application/hal+json"})
@ExposesResourceFor(StateDefinitionResource.class)
public class StateDefinitionsController {
	
	private static final Logger LOG = LogManager.getLogger(Application.APP_LOGGER_NAME);
	
	@Autowired
	private StateDefinitionService stateDefinitionService;
	
	@Autowired
	private RelProvider relProvider;
	
	@Autowired
	private EntityLinks entityLinks;
	
	// TODO Refactor HATEOAS link building. Time for ResourceAssembler or moving this stuff up the service. Although moving it up will create a circular dep.
	
	@RequestMapping(value="", method={RequestMethod.GET, RequestMethod.HEAD})
	public Resources<StateDefinitionResource> getStateDefinitions() throws StateDefinitionNotFoundException, ProviderNotFoundException, AttributeDefinitionNotFoundException {
		if (LOG.isTraceEnabled()) {
			LOG.entry();
		}
		
		final Resources<StateDefinitionResource> resources = getStateDefinitions(stateDefinitionService.getStateDefinitions());
		
		
		if (LOG.isTraceEnabled()) {
			LOG.exit(resources);	
		}
		return resources;
	}
	
	@RequestMapping(value="", params="provider", method={RequestMethod.GET, RequestMethod.HEAD})
	public Resources<StateDefinitionResource> getStateDefinitions(@RequestParam UUID provider) throws ProviderNotFoundException, StateDefinitionNotFoundException, AttributeDefinitionNotFoundException {
		if (LOG.isTraceEnabled()) {
			LOG.entry();
		}
		
		final Resources<StateDefinitionResource> resources = getStateDefinitions(stateDefinitionService.getStateDefinitionsByProvider(provider));
		
		if (LOG.isTraceEnabled()) {
			LOG.exit(resources);	
		}
		return resources;
	}
	
	private Resources<StateDefinitionResource> getStateDefinitions(Collection<StateDefinition> stateDefinitions) throws StateDefinitionNotFoundException, ProviderNotFoundException, AttributeDefinitionNotFoundException {
		if (LOG.isTraceEnabled()) {
			LOG.entry(stateDefinitions);
		}

		final List<StateDefinitionResource> stateDefinitionResources = new ArrayList<StateDefinitionResource>(stateDefinitions.size());
		
		for (StateDefinition stateDefinition : stateDefinitions) {
			final StateDefinitionResource resource = new StateDefinitionResource(stateDefinition);
			resource.add(linkTo(methodOn(StateDefinitionsController.class).getStateDefinition(stateDefinition.getUUID())).withSelfRel());
			resource.add(linkTo(methodOn(StateDefinitionsController.class).getAttributeDefinitions(stateDefinition.getUUID())).withRel("attributeDefinitions"));
			stateDefinitionResources.add(resource);
		}
		
		final Resources<StateDefinitionResource> resources = new Resources<StateDefinitionResource>(stateDefinitionResources);
		resources.add(entityLinks.linkToCollectionResource(StateDefinitionResource.class));
		
		
		if (LOG.isTraceEnabled()) {
			LOG.exit(resources);	
		}
		return resources;
	}
	
	@RequestMapping(value="/{uuid}", method={RequestMethod.GET, RequestMethod.HEAD})
	public StateDefinitionResource getStateDefinition(@PathVariable UUID uuid) throws StateDefinitionNotFoundException, ProviderNotFoundException, AttributeDefinitionNotFoundException {
		if (LOG.isTraceEnabled()) {
			LOG.entry(uuid);
		}
		
		final StateDefinition stateDefinition = stateDefinitionService.getStateDefinition(uuid);
		final StateDefinitionResource resource = new StateDefinitionResource(stateDefinition);
		resource.add(linkTo(methodOn(StateDefinitionsController.class).getStateDefinition(stateDefinition.getUUID())).withSelfRel());
		resource.add(linkTo(methodOn(StateDefinitionsController.class).getAttributeDefinitions(uuid)).withRel("attributeDefinitions"));
		
		
		if (LOG.isTraceEnabled()) {
			LOG.exit(resource);
		}
		return resource;
	}
	
	@RequestMapping(value="/{stateUuid}/attributeDefinitions", method={RequestMethod.GET, RequestMethod.HEAD})
	public Resources<AttributeDefinitionResource> getAttributeDefinitions(@PathVariable UUID stateUuid) throws StateDefinitionNotFoundException, ProviderNotFoundException, AttributeDefinitionNotFoundException {
		if (LOG.isTraceEnabled()) {
			LOG.entry(stateUuid);
		}
		
		final List<AttributeDefinition> attributeDefinitions = stateDefinitionService.getAttributeDefinitions(stateUuid);
		final List<AttributeDefinitionResource> attributeDefinitionResources = new ArrayList<AttributeDefinitionResource>(attributeDefinitions.size());
		
		
		for (AttributeDefinition attributeDefinition : attributeDefinitions) {
			final AttributeDefinitionResource resource = new AttributeDefinitionResource(attributeDefinition);
			resource.add(linkTo(methodOn(StateDefinitionsController.class).getAttributeDefinition(stateUuid, attributeDefinition.getUUID())).withSelfRel());
	
			attributeDefinitionResources.add(resource);
		}
		
		final Resources<AttributeDefinitionResource> resources = new Resources<AttributeDefinitionResource>(attributeDefinitionResources);
		resources.add(linkTo(methodOn(StateDefinitionsController.class).getAttributeDefinitions(stateUuid)).withSelfRel());
	
		if (LOG.isTraceEnabled()) {
			LOG.exit(resources);	
		}
		return resources;
	}
	
	@RequestMapping(value="/{stateUuid}/attributeDefinitions/{uuid}", method={RequestMethod.GET, RequestMethod.HEAD})
	public AttributeDefinitionResource getAttributeDefinition(@PathVariable UUID stateUuid, @PathVariable UUID uuid) throws StateDefinitionNotFoundException, ProviderNotFoundException, AttributeDefinitionNotFoundException {
		if (LOG.isTraceEnabled()) {
			LOG.entry(stateUuid, uuid);
		}
		
		final AttributeDefinition attributeDefinition = stateDefinitionService.getAttributeDefinition(stateUuid, uuid);

		final AttributeDefinitionResource resource = new AttributeDefinitionResource(attributeDefinition);
		resource.add(linkTo(methodOn(StateDefinitionsController.class).getAttributeDefinition(stateUuid, attributeDefinition.getUUID())).withSelfRel());
		resource.add(linkTo(methodOn(StateDefinitionsController.class).getAttributeDefinitionValues(stateUuid, attributeDefinition.getUUID())).withRel("values"));
		
		if (LOG.isTraceEnabled()) {
			LOG.exit(resource);	
		}
		return resource;
	}
	
	@RequestMapping(value="/{stateUuid}/attributeDefinitions/{uuid}/values", method={RequestMethod.GET, RequestMethod.HEAD})
	public ResponseEntity<Resource<Map<String, String>>> getAttributeDefinitionValues(@PathVariable UUID stateUuid, @PathVariable UUID uuid) throws StateDefinitionNotFoundException, ProviderNotFoundException, AttributeDefinitionNotFoundException {
		if (LOG.isTraceEnabled()) {
			LOG.entry(stateUuid, uuid);
		}
		
		final Map<String, String> attributeDefinitionValues = stateDefinitionService.getAttributeDefinitionValues(stateUuid, uuid);

		ResponseEntity<Resource<Map<String, String>>> response;
		if (attributeDefinitionValues == null) {
			response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			final Resource<Map<String, String>> resource = new Resource<Map<String, String>>(attributeDefinitionValues);
			resource.add(linkTo(methodOn(StateDefinitionsController.class).getAttributeDefinitionValues(stateUuid, uuid)).withSelfRel());
			response = new ResponseEntity<>(resource, HttpStatus.OK);
		} 
		
		if (LOG.isTraceEnabled()) {
			LOG.exit(response);	
		}
		return response;
	}

}
