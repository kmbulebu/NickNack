package com.oakcity.nicknack.server.model;

import java.util.List;
import java.util.UUID;

import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.oakcity.nicknack.core.actions.ActionDefinition;
import com.oakcity.nicknack.core.actions.ParameterDefinition;

@Relation(value="ActionDefinition", collectionRelation="ActionDefinitions")
public class ActionDefinitionResource extends ResourceSupport implements ActionDefinition {
	
	@JsonIgnore
	private final ActionDefinition actionDefinition;
	
	public ActionDefinitionResource(ActionDefinition actionDefinition) {
		this.actionDefinition = actionDefinition;
	}

	@Override
	public UUID getUUID() {
		return actionDefinition.getUUID();
	}

	@Override
	public String getName() {
		return actionDefinition.getName();
	}

	@Override
	@JsonIgnore
	public List<ParameterDefinition> getParameterDefinitions() {
		return actionDefinition.getParameterDefinitions();
	}

	@Override
	public UUID getProviderUUID() {
		return actionDefinition.getProviderUUID();
	}

}
