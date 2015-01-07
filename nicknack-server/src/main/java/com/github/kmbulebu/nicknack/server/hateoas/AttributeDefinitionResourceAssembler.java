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

import com.github.kmbulebu.nicknack.core.attributes.AttributeDefinition;
import com.github.kmbulebu.nicknack.server.model.AttributeDefinitionResource;
import com.github.kmbulebu.nicknack.server.services.exceptions.ActionDefinitionNotFoundException;
import com.github.kmbulebu.nicknack.server.services.exceptions.AttributeDefinitionNotFoundException;
import com.github.kmbulebu.nicknack.server.web.ActionDefinitionAttributeDefinitionsController;

@Component
@Scope(value="prototype")
public class AttributeDefinitionResourceAssembler extends
		ResourceAssemblerSupport<AttributeDefinition, AttributeDefinitionResource> {

	@Autowired
	private EntityLinks links;

	@Autowired
	private RelProvider rels;
	
	private final UUID actionUuid;

	public AttributeDefinitionResourceAssembler(UUID actionUuid) {
		super(ActionDefinitionAttributeDefinitionsController.class, AttributeDefinitionResource.class);
		this.actionUuid = actionUuid;
	}

	@Override
	public AttributeDefinitionResource toResource(AttributeDefinition entity) {
		final AttributeDefinitionResource resource = instantiateResource(entity);
		try {
			resource.add(linkTo(methodOn(ActionDefinitionAttributeDefinitionsController.class).getAttributeDefinition(actionUuid, entity.getUUID())).withSelfRel());
		} catch (ActionDefinitionNotFoundException | AttributeDefinitionNotFoundException e) {
			throw new RuntimeException("Can not happen", e);
		}
		
		return resource;
	}

	@Override
	protected AttributeDefinitionResource instantiateResource(AttributeDefinition entity) {
		return new AttributeDefinitionResource(entity);
	}
	
	public Resources<AttributeDefinitionResource> wrap(Iterable<? extends AttributeDefinition> entities) {
		final Resources<AttributeDefinitionResource> resources = new Resources<>(toResources(entities));
		try {
			resources.add(linkTo(methodOn(ActionDefinitionAttributeDefinitionsController.class).getAttributeDefinitions(actionUuid)).withSelfRel());
		} catch (ActionDefinitionNotFoundException | AttributeDefinitionNotFoundException e) {
			throw new RuntimeException("Can not happen", e);
		}
		return resources;
	}
	
}
