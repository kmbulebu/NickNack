package com.oakcity.nicknack.providers.xbmc.events;

import java.util.Arrays;
import java.util.UUID;

import com.oakcity.nicknack.core.events.AttributeDefinition;
import com.oakcity.nicknack.core.events.impl.BasicTimestampedEventDefinition;

public abstract class BaseEventDefinition extends BasicTimestampedEventDefinition {

	public BaseEventDefinition(UUID uuid, String name, AttributeDefinition... attributeDefinitions) {
		super(uuid, name, addAttributeDefinition(SourceHostAttributeDefinition.INSTANCE, attributeDefinitions));
	}
	
	private static final AttributeDefinition[] addAttributeDefinition(AttributeDefinition toAdd, AttributeDefinition... attributeDefinitions) {
		AttributeDefinition[] newArray;
		if (attributeDefinitions == null) {
			newArray = new AttributeDefinition[1];
		} else {
			newArray = Arrays.copyOf(attributeDefinitions, attributeDefinitions.length + 1);
		}
		
		newArray[newArray.length - 1] = toAdd;
		
		return newArray;
	}
	

}
