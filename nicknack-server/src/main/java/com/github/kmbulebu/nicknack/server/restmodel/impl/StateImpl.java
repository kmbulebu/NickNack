package com.github.kmbulebu.nicknack.server.restmodel.impl;

import java.util.List;
import java.util.UUID;

import com.github.kmbulebu.nicknack.server.restmodel.Attribute;
import com.github.kmbulebu.nicknack.server.restmodel.State;
import com.github.kmbulebu.nicknack.server.restmodel.StateDefinition;

public class StateImpl implements State {
	
	private final StateDefinition stateDefinition;
	private List<Attribute> attributes;
	
	public StateImpl(StateDefinition stateDefinition) {
		this.stateDefinition = stateDefinition;
	}

	@Override
	public UUID getUuid() {
		return stateDefinition.getUuid();
	}

	@Override
	public String getName() {
		return stateDefinition.getName();
	}

	@Override
	public List<Attribute> getAttributes() {
		return attributes;
	}
	
	public void setAttributes(List<Attribute> attributes) {
		this.attributes = attributes;
	}

}
