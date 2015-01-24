package com.github.kmbulebu.nicknack.server.model;

import java.util.List;
import java.util.UUID;

import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.kmbulebu.nicknack.core.attributes.AttributeDefinition;
import com.github.kmbulebu.nicknack.core.states.StateDefinition;

@Relation(value="StateDefinition", collectionRelation="StateDefinitions")
public class StateDefinitionResource extends ResourceSupport implements StateDefinition {
	
	@JsonIgnore
	private final StateDefinition stateDefinition;
	
	public StateDefinitionResource(StateDefinition stateDefinition) {
		this.stateDefinition = stateDefinition;
	}

	@Override
	public UUID getUUID() {
		return stateDefinition.getUUID();
	}

	@Override
	public String getName() {
		return stateDefinition.getName();
	}

	@Override
	@JsonIgnore
	public List<AttributeDefinition<?>> getAttributeDefinitions() {
		return stateDefinition.getAttributeDefinitions();
	}

}
