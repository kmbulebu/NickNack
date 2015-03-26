package com.github.kmbulebu.nicknack.server.dbmodel;

import java.util.UUID;

import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;

@NodeEntity
public class AttributeEntity {

	@GraphId 
	private Long id;
	
	@Indexed(unique=true)
	private UUID attributeDefinitionUuid;
	
	private String[] values;
	
	private boolean multiValue;

	public UUID getAttributeDefinitionUuid() {
		return attributeDefinitionUuid;
	}

	public void setAttributeDefinitionUuid(UUID attributeDefinitionUuid) {
		this.attributeDefinitionUuid = attributeDefinitionUuid;
	}

	public String[] getValues() {
		return values;
	}

	public void setValues(String[] values) {
		this.values = values;
	}

	public boolean isMultiValue() {
		return multiValue;
	}

	public void setMultiValue(boolean multiValue) {
		this.multiValue = multiValue;
	}
	
	
	
}
