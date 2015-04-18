package com.github.kmbulebu.nicknack.server.dbmodel;

import java.util.UUID;

import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.fieldaccess.DynamicProperties;

@NodeEntity
public class ActionEntity {

	@GraphId 
	private Long id;
	
	@Indexed
	private UUID actionDefinitionUuid;
	
	private String name;
	
	private DynamicProperties attributes;
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public UUID getActionDefinitionUuid() {
		return actionDefinitionUuid;
	}

	public void setActionDefinitionUuid(UUID actionDefinitionUuid) {
		this.actionDefinitionUuid = actionDefinitionUuid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public DynamicProperties getAttributes() {
		return attributes;
	}
	
	public void setAttributes(DynamicProperties attributes) {
		this.attributes = attributes;
	}
	
}
