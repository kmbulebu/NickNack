package com.github.kmbulebu.nicknack.server.web;

import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.kmbulebu.nicknack.server.Application;
import com.github.kmbulebu.nicknack.server.hateoas.ActionDefinitionResourceAssembler;
import com.github.kmbulebu.nicknack.server.model.ActionDefinitionResource;
import com.github.kmbulebu.nicknack.server.services.ActionDefinitionService;
import com.github.kmbulebu.nicknack.server.services.exceptions.ActionDefinitionNotFoundException;
import com.github.kmbulebu.nicknack.server.services.exceptions.AttributeDefinitionNotFoundException;
import com.github.kmbulebu.nicknack.server.services.exceptions.ProviderNotFoundException;

@RestController
@RequestMapping(value="/api/actionDefinitions", produces={"application/hal+json"})
@ExposesResourceFor(ActionDefinitionResource.class)
public class ActionDefinitionsController {
	
	private static final Logger LOG = LogManager.getLogger(Application.APP_LOGGER_NAME);
	
	@Autowired
	private ActionDefinitionService actionDefinitionService;
	
	@Autowired
	private ActionDefinitionResourceAssembler actionDefinitionResourceAssembler;
	
	@RequestMapping(value="", method={RequestMethod.GET, RequestMethod.HEAD})
	public Resources<ActionDefinitionResource> getActionDefinitions() throws ActionDefinitionNotFoundException, AttributeDefinitionNotFoundException {
		if (LOG.isTraceEnabled()) {
			LOG.entry();
		}
		
		final Resources<ActionDefinitionResource> resources = actionDefinitionResourceAssembler.wrap(actionDefinitionService.getActionDefinitions());
		
		if (LOG.isTraceEnabled()) {
			LOG.exit(resources);	
		}
		return resources;
	}
	
	@RequestMapping(value="", params="provider", method={RequestMethod.GET, RequestMethod.HEAD})
	public Resources<ActionDefinitionResource> getActionDefinitions(@RequestParam UUID provider) throws ActionDefinitionNotFoundException, AttributeDefinitionNotFoundException, ProviderNotFoundException {
		if (LOG.isTraceEnabled()) {
			LOG.entry();
		}
		
		final Resources<ActionDefinitionResource> resources = actionDefinitionResourceAssembler.wrap(actionDefinitionService.getActionDefinitionsByProvider(provider), provider);
		
		if (LOG.isTraceEnabled()) {
			LOG.exit(resources);	
		}
		return resources;
	}
	
	@RequestMapping(value="/{uuid}", method={RequestMethod.GET, RequestMethod.HEAD})
	public ActionDefinitionResource getActionDefinition(@PathVariable UUID uuid) throws ActionDefinitionNotFoundException, AttributeDefinitionNotFoundException {
		if (LOG.isTraceEnabled()) {
			LOG.entry(uuid);
		}
		
		final ActionDefinitionResource resource = actionDefinitionResourceAssembler.toResource(actionDefinitionService.getActionDefinition(uuid));		
		
		if (LOG.isTraceEnabled()) {
			LOG.exit(resource);
		}
		return resource;
	}

}
