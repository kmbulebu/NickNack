package com.oakcity.nicknack.server.web;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

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

import com.oakcity.nicknack.core.actions.Action.ActionDefinition;
import com.oakcity.nicknack.core.actions.Action.ParameterDefinition;
import com.oakcity.nicknack.server.AppConfiguration;
import com.oakcity.nicknack.server.model.ActionDefinitionResource;
import com.oakcity.nicknack.server.model.ParameterDefinitionResource;
import com.oakcity.nicknack.server.services.ActionDefinitionService;

@RestController
@RequestMapping(value="/actionDefinitions", produces={MediaType.APPLICATION_JSON_VALUE})
@ExposesResourceFor(ActionDefinitionResource.class)
public class ActionDefinitionsController {
	
	private static final Logger LOG = LogManager.getLogger(AppConfiguration.APP_LOGGER_NAME);
	
	@Autowired
	private ActionDefinitionService actionDefinitionService;
	
	@Autowired
	private RelProvider relProvider;
	
	@Autowired
	private EntityLinks entityLinks;
	
	// TODO Refactor HATEOAS link building. Time for ResourceAssembler or moving this stuff up the service. Although moving it up will create a circular dep.
	
	@RequestMapping(value="")
	public Resources<ActionDefinitionResource> getActionDefinitions() {
		if (LOG.isTraceEnabled()) {
			LOG.entry();
		}
		
		final List<ActionDefinition> actionDefinitions = actionDefinitionService.getActionDefinitions();
		
		final List<ActionDefinitionResource> actionDefinitionResources = new ArrayList<ActionDefinitionResource>(actionDefinitions.size());
		
		for (ActionDefinition actionDefinition : actionDefinitions) {
			final ActionDefinitionResource resource = new ActionDefinitionResource(actionDefinition);
			resource.add(linkTo(methodOn(ActionDefinitionsController.class).getActionDefinition(actionDefinition.getUUID())).withSelfRel());
			resource.add(linkTo(methodOn(ActionDefinitionsController.class).getParameterDefinitions(actionDefinition.getUUID())).withRel("parameterDefinitions"));
			actionDefinitionResources.add(resource);
		}
		
		final Resources<ActionDefinitionResource> resources = new Resources<ActionDefinitionResource>(actionDefinitionResources);
		resources.add(entityLinks.linkToCollectionResource(ActionDefinitionResource.class));
		
		
		if (LOG.isTraceEnabled()) {
			LOG.exit(resources);	
		}
		return resources;
	}
	
	@RequestMapping(value="/{uuid}")
	public ActionDefinitionResource getActionDefinition(@PathVariable UUID uuid) {
		if (LOG.isTraceEnabled()) {
			LOG.entry(uuid);
		}
		
		final ActionDefinition actionDefinition = actionDefinitionService.getActionDefinition(uuid);
		final ActionDefinitionResource resource = new ActionDefinitionResource(actionDefinition);
		resource.add(linkTo(methodOn(ActionDefinitionsController.class).getActionDefinition(actionDefinition.getUUID())).withSelfRel());
		resource.add(linkTo(methodOn(ActionDefinitionsController.class).getParameterDefinitions(uuid)).withRel("parameterDefinitions"));
		
		
		if (LOG.isTraceEnabled()) {
			LOG.exit(resource);
		}
		return resource;
	}
	
	@RequestMapping(value="/{actionUuid}/parameterDefinitions")
	public Resources<ParameterDefinitionResource> getParameterDefinitions(@PathVariable UUID actionUuid) {
		if (LOG.isTraceEnabled()) {
			LOG.entry(actionUuid);
		}
		
		final List<ParameterDefinition> parameterDefinitions = actionDefinitionService.getParameterDefinitions(actionUuid);
		final List<ParameterDefinitionResource> parameterDefinitionResources = new ArrayList<ParameterDefinitionResource>(parameterDefinitions.size());
		
		
		for (ParameterDefinition parameterDefinition : parameterDefinitions) {
			final ParameterDefinitionResource resource = new ParameterDefinitionResource(parameterDefinition);
			resource.add(linkTo(methodOn(ActionDefinitionsController.class).getParameterDefinition(actionUuid, parameterDefinition.getUUID())).withSelfRel());
	
			parameterDefinitionResources.add(resource);
		}
		
		final Resources<ParameterDefinitionResource> resources = new Resources<ParameterDefinitionResource>(parameterDefinitionResources);
		resources.add(linkTo(methodOn(ActionDefinitionsController.class).getParameterDefinitions(actionUuid)).withSelfRel());
	
		if (LOG.isTraceEnabled()) {
			LOG.exit(resources);	
		}
		return resources;
	}
	
	@RequestMapping(value="/{actionUuid}/parameterDefinitions/{uuid}")
	public ParameterDefinitionResource getParameterDefinition(@PathVariable UUID actionUuid, @PathVariable UUID uuid) {
		if (LOG.isTraceEnabled()) {
			LOG.entry(actionUuid, uuid);
		}
		
		final ParameterDefinition parameterDefinition = actionDefinitionService.getParameterDefinition(actionUuid, uuid);

		final ParameterDefinitionResource resource = new ParameterDefinitionResource(parameterDefinition);
		resource.add(linkTo(methodOn(ActionDefinitionsController.class).getParameterDefinition(actionUuid, parameterDefinition.getUUID())).withSelfRel());
		
		if (LOG.isTraceEnabled()) {
			LOG.exit(resource);	
		}
		return resource;
	}

}
