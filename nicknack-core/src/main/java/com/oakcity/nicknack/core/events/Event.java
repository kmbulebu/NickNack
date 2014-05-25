package com.oakcity.nicknack.core.events;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.oakcity.nicknack.core.Unit;



/**
 * An event.
 * @author Kevin Bulebush
 *
 */
public interface Event {
	
	// Are we adequately tying Events to their definition?
	
	// Attribute definition to Attribute Value map.
	public Map<UUID, String> getAttributes();
	
	public EventDefinition getEventDefinition();
	
	public interface EventDefinition {
		
		public UUID getUUID();
		
		public String getName();
		
		// Group or 'parent'. The plugin or device that owns this.
		
		public List<AttributeDefinition> getAttributeDefinitions();
		
	}
	
	public interface AttributeDefinition {
		
		public UUID getUUID();
		
		public String getName();
		
		public Unit<?> getUnits();
		
		public boolean isOptional();
	}
	
	
}
