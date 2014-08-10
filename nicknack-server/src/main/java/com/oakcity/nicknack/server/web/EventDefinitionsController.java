package com.oakcity.nicknack.server.web;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.oakcity.nicknack.core.events.AttributeDefinition;
import com.oakcity.nicknack.core.events.EventDefinition;
import com.oakcity.nicknack.server.Application;
import com.oakcity.nicknack.server.model.AttributeDefinitionResource;
import com.oakcity.nicknack.server.model.EventDefinitionResource;
import com.oakcity.nicknack.server.services.EventDefinitionService;

@RestController
@RequestMapping(value="/api/eventDefinitions", produces={"application/hal+json"})
@ExposesResourceFor(EventDefinitionResource.class)
public class EventDefinitionsController {
	
	private static final Logger LOG = LogManager.getLogger(Application.APP_LOGGER_NAME);
	
	@Autowired
	private EventDefinitionService eventDefinitionService;
	
	@Autowired
	private RelProvider relProvider;
	
	@Autowired
	private EntityLinks entityLinks;
	
	// TODO Refactor HATEOAS link building. Time for ResourceAssembler or moving this stuff up the service. Although moving it up will create a circular dep.
	
	@RequestMapping(value="", method={RequestMethod.GET, RequestMethod.HEAD})
	public Resources<EventDefinitionResource> getEventDefinitions() {
		if (LOG.isTraceEnabled()) {
			LOG.entry();
		}
		
		final Resources<EventDefinitionResource> resources = getEventDefinitions(eventDefinitionService.getEventDefinitions());
		
		
		if (LOG.isTraceEnabled()) {
			LOG.exit(resources);	
		}
		return resources;
	}
	
	@RequestMapping(value="", params="provider", method={RequestMethod.GET, RequestMethod.HEAD})
	public Resources<EventDefinitionResource> getEventDefinitions(@RequestParam UUID provider) {
		if (LOG.isTraceEnabled()) {
			LOG.entry();
		}
		
		final Resources<EventDefinitionResource> resources = getEventDefinitions(eventDefinitionService.getEventDefinitionsByProvider(provider));
		
		if (LOG.isTraceEnabled()) {
			LOG.exit(resources);	
		}
		return resources;
	}
	
	private Resources<EventDefinitionResource> getEventDefinitions(Collection<EventDefinition> eventDefinitions) {
		if (LOG.isTraceEnabled()) {
			LOG.entry(eventDefinitions);
		}

		final List<EventDefinitionResource> eventDefinitionResources = new ArrayList<EventDefinitionResource>(eventDefinitions.size());
		
		for (EventDefinition eventDefinition : eventDefinitions) {
			final EventDefinitionResource resource = new EventDefinitionResource(eventDefinition);
			resource.add(linkTo(methodOn(EventDefinitionsController.class).getEventDefinition(eventDefinition.getUUID())).withSelfRel());
			resource.add(linkTo(methodOn(EventDefinitionsController.class).getAttributeDefinitions(eventDefinition.getUUID())).withRel("attributeDefinitions"));
			eventDefinitionResources.add(resource);
		}
		
		final Resources<EventDefinitionResource> resources = new Resources<EventDefinitionResource>(eventDefinitionResources);
		resources.add(entityLinks.linkToCollectionResource(EventDefinitionResource.class));
		
		
		if (LOG.isTraceEnabled()) {
			LOG.exit(resources);	
		}
		return resources;
	}
	
	@RequestMapping(value="/{uuid}", method={RequestMethod.GET, RequestMethod.HEAD})
	public EventDefinitionResource getEventDefinition(@PathVariable UUID uuid) {
		if (LOG.isTraceEnabled()) {
			LOG.entry(uuid);
		}
		
		final EventDefinition eventDefinition = eventDefinitionService.getEventDefinition(uuid);
		final EventDefinitionResource resource = new EventDefinitionResource(eventDefinition);
		resource.add(linkTo(methodOn(EventDefinitionsController.class).getEventDefinition(eventDefinition.getUUID())).withSelfRel());
		resource.add(linkTo(methodOn(EventDefinitionsController.class).getAttributeDefinitions(uuid)).withRel("attributeDefinitions"));
		
		
		if (LOG.isTraceEnabled()) {
			LOG.exit(resource);
		}
		return resource;
	}
	
	@RequestMapping(value="/{eventUuid}/attributeDefinitions", method={RequestMethod.GET, RequestMethod.HEAD})
	public Resources<AttributeDefinitionResource> getAttributeDefinitions(@PathVariable UUID eventUuid) {
		if (LOG.isTraceEnabled()) {
			LOG.entry(eventUuid);
		}
		
		final List<AttributeDefinition> attributeDefinitions = eventDefinitionService.getAttributeDefinitions(eventUuid);
		final List<AttributeDefinitionResource> attributeDefinitionResources = new ArrayList<AttributeDefinitionResource>(attributeDefinitions.size());
		
		
		for (AttributeDefinition attributeDefinition : attributeDefinitions) {
			final AttributeDefinitionResource resource = new AttributeDefinitionResource(attributeDefinition);
			resource.add(linkTo(methodOn(EventDefinitionsController.class).getAttributeDefinition(eventUuid, attributeDefinition.getUUID())).withSelfRel());
	
			attributeDefinitionResources.add(resource);
		}
		
		final Resources<AttributeDefinitionResource> resources = new Resources<AttributeDefinitionResource>(attributeDefinitionResources);
		resources.add(linkTo(methodOn(EventDefinitionsController.class).getAttributeDefinitions(eventUuid)).withSelfRel());
	
		if (LOG.isTraceEnabled()) {
			LOG.exit(resources);	
		}
		return resources;
	}
	
	@RequestMapping(value="/{eventUuid}/attributeDefinitions/{uuid}", method={RequestMethod.GET, RequestMethod.HEAD})
	public AttributeDefinitionResource getAttributeDefinition(@PathVariable UUID eventUuid, @PathVariable UUID uuid) {
		if (LOG.isTraceEnabled()) {
			LOG.entry(eventUuid, uuid);
		}
		
		final AttributeDefinition attributeDefinition = eventDefinitionService.getAttributeDefinition(eventUuid, uuid);

		final AttributeDefinitionResource resource = new AttributeDefinitionResource(attributeDefinition);
		resource.add(linkTo(methodOn(EventDefinitionsController.class).getAttributeDefinition(eventUuid, attributeDefinition.getUUID())).withSelfRel());
		resource.add(linkTo(methodOn(EventDefinitionsController.class).getAttributeDefinitionValues(eventUuid, attributeDefinition.getUUID())).withRel("values"));
		
		if (LOG.isTraceEnabled()) {
			LOG.exit(resource);	
		}
		return resource;
	}
	
	@RequestMapping(value="/{eventUuid}/attributeDefinitions/{uuid}/values", method={RequestMethod.GET, RequestMethod.HEAD})
	public Resource<Map<String, String>> getAttributeDefinitionValues(@PathVariable UUID eventUuid, @PathVariable UUID uuid) {
		if (LOG.isTraceEnabled()) {
			LOG.entry(eventUuid, uuid);
		}
		
		final Map<String, String> attributeDefinitionValues = eventDefinitionService.getAttributeDefinitionValues(eventUuid, uuid);

		final Resource<Map<String, String>> resource = new Resource<Map<String, String>>(attributeDefinitionValues);
		resource.add(linkTo(methodOn(EventDefinitionsController.class).getAttributeDefinitionValues(eventUuid, uuid)).withSelfRel());
		
		if (LOG.isTraceEnabled()) {
			LOG.exit(resource);	
		}
		return resource;
	}

}
