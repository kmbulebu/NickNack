package com.oakcity.nicknack.server.model;

import java.util.List;
import java.util.UUID;

import org.springframework.hateoas.ResourceSupport;

import com.oakcity.nicknack.core.events.Event.AttributeDefinition;
import com.oakcity.nicknack.core.events.Event.EventDefinition;

public class EventDefinitionResource extends ResourceSupport implements EventDefinition {
	
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
	public List<AttributeDefinition> getAttributeDefinitions() {
		return eventDefinition.getAttributeDefinitions();
	}

}
