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

import com.github.kmbulebu.nicknack.core.actions.ParameterDefinition;
import com.github.kmbulebu.nicknack.server.Application;
import com.github.kmbulebu.nicknack.server.hateoas.ActionDefinitionResourceAssembler;
import com.github.kmbulebu.nicknack.server.hateoas.ParameterDefinitionResourceAssembler;
import com.github.kmbulebu.nicknack.server.model.ParameterDefinitionResource;
import com.github.kmbulebu.nicknack.server.services.ActionDefinitionService;
import com.github.kmbulebu.nicknack.server.services.exceptions.ActionDefinitionNotFoundException;
import com.github.kmbulebu.nicknack.server.services.exceptions.ParameterDefinitionNotFoundException;

@RestController
@RequestMapping(value="/api/actionDefinitions/{actionUuid}", produces={"application/hal+json"})
@ExposesResourceFor(ParameterDefinitionResource.class)
public class ActionDefinitionParameterDefinitionsController {
	
	private static final Logger LOG = LogManager.getLogger(Application.APP_LOGGER_NAME);
	
	@Autowired
	private ActionDefinitionService actionDefinitionService;
	
	@Autowired
	private ActionDefinitionResourceAssembler actionDefinitionResourceAssembler;
	
	@Autowired
	private BeanFactory beanFactory;
	
	@RequestMapping(value="/parameterDefinitions", method={RequestMethod.GET, RequestMethod.HEAD})
	public Resources<ParameterDefinitionResource> getParameterDefinitions(@PathVariable UUID actionUuid) throws ActionDefinitionNotFoundException, ParameterDefinitionNotFoundException {
		if (LOG.isTraceEnabled()) {
			LOG.entry(actionUuid);
		}
		
		final List<ParameterDefinition> parameterDefinitions = actionDefinitionService.getParameterDefinitions(actionUuid);
		
		final ParameterDefinitionResourceAssembler assembler = beanFactory.getBean(ParameterDefinitionResourceAssembler.class, actionUuid);
		
		final Resources<ParameterDefinitionResource> resources = assembler.wrap(parameterDefinitions);
		
		if (LOG.isTraceEnabled()) {
			LOG.exit(resources);	
		}
		return resources;
	}
	
	@RequestMapping(value="/parameterDefinitions/{uuid}", method={RequestMethod.GET, RequestMethod.HEAD})
	public ParameterDefinitionResource getParameterDefinition(@PathVariable UUID actionUuid, @PathVariable UUID uuid) throws ActionDefinitionNotFoundException, ParameterDefinitionNotFoundException {
		if (LOG.isTraceEnabled()) {
			LOG.entry(actionUuid, uuid);
		}
		
		final ParameterDefinition parameterDefinition = actionDefinitionService.getParameterDefinition(actionUuid, uuid);

		final ParameterDefinitionResourceAssembler assembler = beanFactory.getBean(ParameterDefinitionResourceAssembler.class, actionUuid);
		
		final ParameterDefinitionResource resource = assembler.toResource(parameterDefinition);
		
		if (LOG.isTraceEnabled()) {
			LOG.exit(resource);	
		}
		return resource;
	}

}
