package com.github.kmbulebu.nicknack.server.model;

import java.util.List;
import java.util.UUID;

import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.kmbulebu.nicknack.core.attributes.AttributeDefinition;
import com.github.kmbulebu.nicknack.core.events.EventDefinition;

@Relation(value="EventDefinition", collectionRelation="EventDefinitions")
public class EventDefinitionResource extends ResourceSupport implements EventDefinition {
	
	@JsonIgnore
	private final EventDefinition eventDefinition;
	
	public EventDefinitionResource(EventDefinition eventDefinition) {
		this.eventDefinition = eventDefinition;
	}

	@Override
	public UUID getUUID() {
		return eventDefinition.getUUID();
	}

	@Override
	public String getName() {
		return eventDefinition.getName();
	}

	@Override
	@JsonIgnore
	public List<AttributeDefinition<?>> getAttributeDefinitions() {
		return eventDefinition.getAttributeDefinitions();
	}

}
