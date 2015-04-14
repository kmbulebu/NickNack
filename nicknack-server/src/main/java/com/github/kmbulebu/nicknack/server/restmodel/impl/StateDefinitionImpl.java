package com.github.kmbulebu.nicknack.server.restmodel.impl;

import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonView;
import com.github.kmbulebu.nicknack.server.restmodel.AttributeDefinition;
import com.github.kmbulebu.nicknack.server.restmodel.StateDefinition;
import com.github.kmbulebu.nicknack.server.restmodel.View;

public class StateDefinitionImpl implements StateDefinition {
	
	private UUID uuid;

	private String name;
	
	private List<? extends AttributeDefinition> attributes;
	
	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	@JsonView(View.Summary.class)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<? extends AttributeDefinition> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<? extends AttributeDefinition> attributes) {
		this.attributes = attributes;
	}

}
