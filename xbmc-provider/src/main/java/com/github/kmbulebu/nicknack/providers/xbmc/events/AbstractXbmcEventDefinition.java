package com.github.kmbulebu.nicknack.providers.xbmc.events;

import java.util.Arrays;
import java.util.UUID;

import com.github.kmbulebu.nicknack.core.attributes.AttributeDefinition;
import com.github.kmbulebu.nicknack.core.events.impl.BasicTimestampedEventDefinition;
import com.github.kmbulebu.nicknack.providers.xbmc.attributes.HostAttributeDefinition;

public abstract class AbstractXbmcEventDefinition extends BasicTimestampedEventDefinition {

	public AbstractXbmcEventDefinition(UUID uuid, String name, AttributeDefinition... attributeDefinitions) {
		super(uuid, name, addAttributeDefinition(HostAttributeDefinition.INSTANCE, attributeDefinitions));
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
