package com.github.kmbulebu.nicknack.server.model;

import java.util.List;

import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

import com.github.kmbulebu.nicknack.core.states.State;
import com.github.kmbulebu.nicknack.core.states.StateDefinition;

@Relation(value="States", collectionRelation="Stateses")
public class StatesResource extends ResourceSupport {
	
	private StateDefinition stateDefinition;
	
	private List<State> states;

	public List<State> getStates() {
		return states;
	}

	public void setStates(List<State> states) {
		this.states = states;
	}
	
	public StateDefinition getStateDefinition() {
		return stateDefinition;
	}
	
	public void setStateDefinition(StateDefinition stateDefinition) {
		this.stateDefinition = stateDefinition;
	}
	
}
