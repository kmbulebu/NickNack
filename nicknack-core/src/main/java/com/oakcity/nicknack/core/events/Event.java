package com.oakcity.nicknack.core.events;

import java.util.Map;
import java.util.UUID;



/**
 * An event.
 * @author Kevin Bulebush
 *
 */
public interface Event {
	
	// Are we adequately tying Events to their definition?
	
	// Attribute definition to Attribute Value map.
	public Map<UUID, String> getAttributes();
	
	// TODO Switch to just UUID?
	public EventDefinition getEventDefinition();
	
	
}
