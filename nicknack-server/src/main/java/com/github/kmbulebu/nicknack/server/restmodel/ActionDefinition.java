package com.github.kmbulebu.nicknack.server.restmodel;

import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonView;

public class ActionDefinition {
	
	@JsonView(View.Summary.class)
	private UUID uuid;
	
	@JsonView(View.Summary.class)
	private String name;
	
	@JsonView(View.Summary.class)
	private String description;
	
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<AttributeDefinition> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<AttributeDefinition> attributes) {
		this.attributes = attributes;
	}

}
