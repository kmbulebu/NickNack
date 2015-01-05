package com.github.kmbulebu.nicknack.server.hateoas;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.RelProvider;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import com.github.kmbulebu.nicknack.core.actions.ParameterDefinition;
import com.github.kmbulebu.nicknack.server.model.ParameterDefinitionResource;
import com.github.kmbulebu.nicknack.server.services.exceptions.ActionDefinitionNotFoundException;
import com.github.kmbulebu.nicknack.server.services.exceptions.ParameterDefinitionNotFoundException;
import com.github.kmbulebu.nicknack.server.web.ActionDefinitionParameterDefinitionsController;

@Component
@Scope(value="prototype")
public class ParameterDefinitionResourceAssembler extends
		ResourceAssemblerSupport<ParameterDefinition, ParameterDefinitionResource> {

	@Autowired
	private EntityLinks links;

	@Autowired
	private RelProvider rels;
	
	private final UUID actionUuid;

	public ParameterDefinitionResourceAssembler(UUID actionUuid) {
		super(ActionDefinitionParameterDefinitionsController.class, ParameterDefinitionResource.class);
		this.actionUuid = actionUuid;
	}

	@Override
	public ParameterDefinitionResource toResource(ParameterDefinition entity) {
		final ParameterDefinitionResource resource = instantiateResource(entity);
		try {
			resource.add(linkTo(methodOn(ActionDefinitionParameterDefinitionsController.class).getParameterDefinition(actionUuid, entity.getUUID())).withSelfRel());
		} catch (ActionDefinitionNotFoundException | ParameterDefinitionNotFoundException e) {
			throw new RuntimeException("Can not happen", e);
		}
		
		return resource;
	}

	@Override
	protected ParameterDefinitionResource instantiateResource(ParameterDefinition entity) {
		return new ParameterDefinitionResource(entity);
	}
	
	public Resources<ParameterDefinitionResource> wrap(Iterable<? extends ParameterDefinition> entities) {
		final Resources<ParameterDefinitionResource> resources = new Resources<>(toResources(entities));
		try {
			resources.add(linkTo(methodOn(ActionDefinitionParameterDefinitionsController.class).getParameterDefinitions(actionUuid)).withSelfRel());
		} catch (ActionDefinitionNotFoundException | ParameterDefinitionNotFoundException e) {
			throw new RuntimeException("Can not happen", e);
		}
		return resources;
	}
	
}
