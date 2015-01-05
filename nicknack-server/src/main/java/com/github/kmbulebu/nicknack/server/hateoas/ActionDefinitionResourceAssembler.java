package com.github.kmbulebu.nicknack.server.hateoas;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.Collection;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.RelProvider;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Service;

import com.github.kmbulebu.nicknack.core.actions.ActionDefinition;
import com.github.kmbulebu.nicknack.server.model.ActionDefinitionResource;
import com.github.kmbulebu.nicknack.server.model.ParameterDefinitionResource;
import com.github.kmbulebu.nicknack.server.services.exceptions.ActionDefinitionNotFoundException;
import com.github.kmbulebu.nicknack.server.services.exceptions.ParameterDefinitionNotFoundException;
import com.github.kmbulebu.nicknack.server.services.exceptions.ProviderNotFoundException;
import com.github.kmbulebu.nicknack.server.web.ActionDefinitionParameterDefinitionsController;
import com.github.kmbulebu.nicknack.server.web.ActionDefinitionsController;

@Service
public class ActionDefinitionResourceAssembler extends
		ResourceAssemblerSupport<ActionDefinition, ActionDefinitionResource> {

	@Autowired
	private EntityLinks links;

	@Autowired
	private RelProvider rels;

	public ActionDefinitionResourceAssembler() {
		super(ActionDefinitionsController.class, ActionDefinitionResource.class);
	}

	@Override
	public ActionDefinitionResource toResource(ActionDefinition entity) {
		ActionDefinitionResource resource = instantiateResource(entity);
		try {
			resource.add(links.linkToSingleResource(ActionDefinitionResource.class, entity.getUUID()).withSelfRel());
			resource.add(linkTo(methodOn(ActionDefinitionParameterDefinitionsController.class).getParameterDefinitions(entity.getUUID()))
					.withRel(rels.getCollectionResourceRelFor(ParameterDefinitionResource.class)));
		} catch (ActionDefinitionNotFoundException | ParameterDefinitionNotFoundException e) {
			throw new RuntimeException("Can not happen.");
		}
		
		return resource;
	}

	@Override
	protected ActionDefinitionResource instantiateResource(ActionDefinition entity) {
		return new ActionDefinitionResource(entity);
	}
	
	public Resources<ActionDefinitionResource> wrap(Iterable<? extends ActionDefinition> entities) {
		final Resources<ActionDefinitionResource> resources = new Resources<>(toResources(entities));
		resources.add(links.linkToCollectionResource(ActionDefinitionResource.class).withSelfRel());
		return resources;
	}

	public Resources<ActionDefinitionResource> wrap(Collection<ActionDefinition> entities,
			UUID provider) {
		final Resources<ActionDefinitionResource> resources = new Resources<>(toResources(entities));
		try {
			resources.add(linkTo(methodOn(ActionDefinitionsController.class).getActionDefinitions(provider)).withRel(rels.getCollectionResourceRelFor(ActionDefinitionResource.class)));
		} catch (ActionDefinitionNotFoundException | ParameterDefinitionNotFoundException | ProviderNotFoundException e) {
			throw new RuntimeException("Can not happen.");
		}
		return resources;
	}
	
	
}
