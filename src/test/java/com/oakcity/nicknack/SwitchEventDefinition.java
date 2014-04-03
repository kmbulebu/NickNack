package com.oakcity.nicknack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.oakcity.nicknack.events.Event;
import com.oakcity.nicknack.events.Event.AttributeDefinition;
import com.oakcity.nicknack.events.Event.EventDefinition;
import com.oakcity.nicknack.events.attributes.OnOffPositionAttributeDefinition;

/**
 * Event generated when a switch is moved.
 * @author Kevin Bulebush
 *
 */
public class SwitchEventDefinition implements EventDefinition {
	
	private final List<AttributeDefinition<?, ?>> attributeDefinitions;
	
	public SwitchEventDefinition() {
		attributeDefinitions = new ArrayList<AttributeDefinition<?, ?>>();
		attributeDefinitions.add(new OnOffPositionAttributeDefinition("Position"));
	}

	@Override
	public String getName() {
		return "Switch Toggle";
	}

	@Override
	public List<AttributeDefinition<?, ?>> getAttributeDefinitions() {
		return Collections.unmodifiableList(attributeDefinitions);
	}

	@Override
	public Event newInstance() {
		return new Event() {
			
			private final List<Attribute<?, ?>> attributes = new ArrayList<Attribute<?, ?>>();

			@Override
			public List<Attribute<?, ?>> getAttributes() {
				return attributes;
			}

			@Override
			public EventDefinition getEventDefinition() {
				return SwitchEventDefinition.this;
			}
			
		};
	}

	
}
