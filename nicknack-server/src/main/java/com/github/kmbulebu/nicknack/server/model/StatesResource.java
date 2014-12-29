package com.github.kmbulebu.nicknack.server.model;

import java.util.List;
import java.util.UUID;

import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

import com.github.kmbulebu.nicknack.core.states.State;

@Relation(value="States", collectionRelation="Stateses")
public class StatesResource extends ResourceSupport {
	
	private UUID stateDefinitionUuid;
	
	private List<State> states;

	public UUID getStateDefinitionUuid() {
		return stateDefinitionUuid;
	}

	public void setStateDefinitionUuid(UUID stateDefinitionUuid) {
		this.stateDefinitionUuid = stateDefinitionUuid;
	}

	public List<State> getStates() {
		return states;
	}

	public void setStates(List<State> states) {
		this.states = states;
	}

	@Override
	public String toString() {
		return "StatesResource [stateDefinitionUuid=" + stateDefinitionUuid + ", states=" + states + "]";
	}
	
	
	
}
