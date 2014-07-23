package com.oakcity.nicknack.core.events;

import java.util.List;
import java.util.UUID;

public interface EventDefinition {
	
	public UUID getUUID();
	
	public String getName();
	
	// Group or 'parent'. The plugin or device that owns this.
	
	public List<AttributeDefinition> getAttributeDefinitions();
	
}