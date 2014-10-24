package com.github.kmbulebu.nicknack.server.model;

import java.util.UUID;

import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.kmbulebu.nicknack.core.events.AttributeDefinition;
import com.github.kmbulebu.nicknack.core.units.Unit;

@Relation(value="AttributeDefinition", collectionRelation="AttributeDefinitions")
public class AttributeDefinitionResource extends ResourceSupport implements AttributeDefinition {
		
	@JsonIgnore
	public final AttributeDefinition attributeDefinition;
	
	public AttributeDefinitionResource(AttributeDefinition attributeDefinition) {
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
	public Unit getUnits() {
		return attributeDefinition.getUnits();
	}

	@Override
	public boolean isOptional() {
		return attributeDefinition.isOptional();
	}

}
