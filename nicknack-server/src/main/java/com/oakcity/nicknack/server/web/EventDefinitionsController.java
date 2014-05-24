package com.oakcity.nicknack.server.web;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.RelProvider;
import org.springframework.hateoas.Resources;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oakcity.nicknack.core.events.Event.EventDefinition;
import com.oakcity.nicknack.server.AppConfiguration;
import com.oakcity.nicknack.server.model.EventDefinitionResource;
import com.oakcity.nicknack.server.services.EventDefinitionService;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

@RestController
@RequestMapping(value="/eventDefinitions", produces={MediaType.APPLICATION_JSON_VALUE})
@ExposesResourceFor(EventDefinitionResource.class)
public class EventDefinitionsController {
	
	private static final Logger LOG = LogManager.getLogger(AppConfiguration.APP_LOGGER_NAME);
	
	@Autowired
	private EventDefinitionService eventDefinitionService;
	
	@Autowired
	private RelProvider relProvider;
	
	@Autowired
	private EntityLinks entityLinks;
	
	@RequestMapping(value="")
	public Resources<EventDefinitionResource> getEventDefinitions() {
		if (LOG.isTraceEnabled()) {
			LOG.entry();
		}
		
		final List<EventDefinition> eventDefinitions = eventDefinitionService.getEventDefinitions();
		
		final List<EventDefinitionResource> eventDefinitionResources = new ArrayList<EventDefinitionResource>(eventDefinitions.size());
		
		for (EventDefinition eventDefinition : eventDefinitions) {
			EventDefinitionResource resource = new EventDefinitionResource(eventDefinition);
			resource.add(linkTo(methodOn(EventDefinitionsController.class).getEventDefinition(eventDefinition.getUUID())).withSelfRel());
			eventDefinitionResources.add(resource);
		}
		
		final Resources<EventDefinitionResource> resources = new Resources<EventDefinitionResource>(eventDefinitionResources);
		resources.add(entityLinks.linkToCollectionResource(EventDefinitionResource.class));
		
		
		if (LOG.isTraceEnabled()) {
			LOG.exit(resources);	
		}
		return resources;
	}
	
	@RequestMapping(value="/{uuid}")
	public EventDefinitionResource getEventDefinition(@PathVariable UUID uuid) {
		if (LOG.isTraceEnabled()) {
			LOG.entry(uuid);
		}
		
		final EventDefinition eventDefinition = eventDefinitionService.getEventDefinition(uuid);
		final EventDefinitionResource resource = new EventDefinitionResource(eventDefinition);
		resource.add(linkTo(methodOn(EventDefinitionsController.class).getEventDefinition(eventDefinition.getUUID())).withSelfRel());
		
		if (LOG.isTraceEnabled()) {
			LOG.exit(resource);
		}
		return resource;
	}

}
