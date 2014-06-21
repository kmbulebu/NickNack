package com.oakcity.nicknack.server.model;

import java.util.List;
import java.util.UUID;

import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.oakcity.nicknack.core.actions.Action;
import com.oakcity.nicknack.core.actions.Action.ActionDefinition;
import com.oakcity.nicknack.core.actions.Action.ParameterDefinition;
import com.oakcity.nicknack.core.actions.ActionFailureException;
import com.oakcity.nicknack.core.actions.ActionParameterException;

@Relation(value="ActionDefinition", collectionRelation="ActionDefinitions")
public class ActionDefinitionResource extends ResourceSupport implements ActionDefinition {
	
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

	@Override
	public void run(Action action) throws ActionFailureException, ActionParameterException {
		actionDefinition.run(action);
	}

}
