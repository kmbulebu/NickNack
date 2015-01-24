package com.github.kmbulebu.nicknack.server.model;

import java.util.UUID;

import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.kmbulebu.nicknack.core.attributes.AttributeDefinition;
import com.github.kmbulebu.nicknack.core.valuetypes.ValueType;

@Relation(value="AttributeDefinition", collectionRelation="AttributeDefinitions")
public class AttributeDefinitionResource<T extends ValueType<?>> extends ResourceSupport implements AttributeDefinition<T> {
		
	@JsonIgnore
	public final AttributeDefinition<T> attributeDefinition;
	
	public AttributeDefinitionResource(AttributeDefinition<T> attributeDefinition) {
		this.attributeDefinition = attributeDefinition;
	}

	@Override
	public UUID getUUID() {
		return attributeDefinition.getUUID();
	}

	@Override
	public String getName() {
		return attributeDefinition.getName();
	}

	@Override
	public boolean isRequired() {
		return attributeDefinition.isRequired();
	}

	@Override
	public String getDescription() {
		return attributeDefinition.getDescription();
	}

	@Override
	public T getValueType() {
		return attributeDefinition.getValueType();
	}

}
