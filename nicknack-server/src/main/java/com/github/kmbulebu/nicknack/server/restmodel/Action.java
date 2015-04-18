package com.github.kmbulebu.nicknack.server.restmodel;

import java.util.Map;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonView;

public class Action {
	
	@JsonView(View.Summary.class)
	private Long id;

	@JsonView(View.Summary.class)
	private UUID uuid;
	
	@JsonView(View.Summary.class)
	private String name;
	
	private Map<UUID, String[]> attributes;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

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

	public Map<UUID, String[]> getAttributes() {
		return attributes;
	}
	
	public void setAttributes(Map<UUID, String[]> attributes) {
		this.attributes = attributes;
	}
}
