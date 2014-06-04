package com.oakcity.nicknack.server.model;

import java.util.UUID;

import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

import com.oakcity.nicknack.core.Unit;
import com.oakcity.nicknack.core.events.Event.AttributeDefinition;

@Relation(value="AttributeDefinition", collectionRelation="AttributeDefinitions")
public class AttributeDefinitionResource extends ResourceSupport implements AttributeDefinition {
		
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
	public Unit<?> getUnits() {
		return attributeDefinition.getUnits();
	}

	@Override
	public boolean isOptional() {
		return attributeDefinition.isOptional();
	}

}
