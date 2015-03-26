package com.github.kmbulebu.nicknack.server.restmodel;

import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonView;

public class StateDefinition {
	
	@JsonView(View.Summary.class)
	private UUID uuid;
	
	@JsonView(View.Summary.class)
	private String name;
	
	private List<AttributeDefinition> attributes;

	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<AttributeDefinition> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<AttributeDefinition> attributes) {
		this.attributes = attributes;
	}

}
