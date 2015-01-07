package com.github.kmbulebu.nicknack.server.web;

import java.util.List;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.kmbulebu.nicknack.core.attributes.AttributeDefinition;
import com.github.kmbulebu.nicknack.server.Application;
import com.github.kmbulebu.nicknack.server.hateoas.ActionDefinitionResourceAssembler;
import com.github.kmbulebu.nicknack.server.hateoas.AttributeDefinitionResourceAssembler;
import com.github.kmbulebu.nicknack.server.model.AttributeDefinitionResource;
import com.github.kmbulebu.nicknack.server.services.ActionDefinitionService;
import com.github.kmbulebu.nicknack.server.services.exceptions.ActionDefinitionNotFoundException;
import com.github.kmbulebu.nicknack.server.services.exceptions.AttributeDefinitionNotFoundException;

@RestController
@RequestMapping(value="/api/actionDefinitions/{actionUuid}", produces={"application/hal+json"})
@ExposesResourceFor(AttributeDefinitionResource.class)
public class ActionDefinitionAttributeDefinitionsController {
	
	private static final Logger LOG = LogManager.getLogger(Application.APP_LOGGER_NAME);
	
	@Autowired
	private ActionDefinitionService actionDefinitionService;
	
	@Autowired
	private ActionDefinitionResourceAssembler actionDefinitionResourceAssembler;
	
	@Autowired
	private BeanFactory beanFactory;
	
	@RequestMapping(value="/attributeDefinitions", method={RequestMethod.GET, RequestMethod.HEAD})
	public Resources<AttributeDefinitionResource> getAttributeDefinitions(@PathVariable UUID actionUuid) throws ActionDefinitionNotFoundException, AttributeDefinitionNotFoundException {
		if (LOG.isTraceEnabled()) {
			LOG.entry(actionUuid);
		}
		
		final List<AttributeDefinition> attributeDefinitions = actionDefinitionService.getAttributeDefinitions(actionUuid);
		
		final AttributeDefinitionResourceAssembler assembler = beanFactory.getBean(AttributeDefinitionResourceAssembler.class, actionUuid);
		
		final Resources<AttributeDefinitionResource> resources = assembler.wrap(attributeDefinitions);
		
		if (LOG.isTraceEnabled()) {
			LOG.exit(resources);	
		}
		return resources;
	}
	
	@RequestMapping(value="/attributeDefinitions/{uuid}", method={RequestMethod.GET, RequestMethod.HEAD})
	public AttributeDefinitionResource getAttributeDefinition(@PathVariable UUID actionUuid, @PathVariable UUID uuid) throws ActionDefinitionNotFoundException, AttributeDefinitionNotFoundException {
		if (LOG.isTraceEnabled()) {
			LOG.entry(actionUuid, uuid);
		}
		
		final AttributeDefinition attributeDefinition = actionDefinitionService.getAttributeDefinition(actionUuid, uuid);

		final AttributeDefinitionResourceAssembler assembler = beanFactory.getBean(AttributeDefinitionResourceAssembler.class, actionUuid);
		
		final AttributeDefinitionResource resource = assembler.toResource(attributeDefinition);
		
		if (LOG.isTraceEnabled()) {
			LOG.exit(resource);	
		}
		return resource;
	}

}
