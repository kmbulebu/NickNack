package com.github.kmbulebu.nicknack.core.events;

import java.util.List;
import java.util.UUID;

import com.github.kmbulebu.nicknack.core.attributes.AttributeDefinition;

public interface EventDefinition {
	
	public UUID getUUID();
	
	public String getName();
	
	// Group or 'parent'. The plugin or device that owns this.
	
	public List<AttributeDefinition> getAttributeDefinitions();
	
}