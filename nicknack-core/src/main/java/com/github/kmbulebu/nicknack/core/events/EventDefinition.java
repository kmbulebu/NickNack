package com.github.kmbulebu.nicknack.core.events;

import java.util.List;
import java.util.UUID;

import com.github.kmbulebu.nicknack.core.attributes.AttributeDefinition;

/**
 * Define's an Event that a provider may fire.
 *
 */
public interface EventDefinition {
	
	/**
	 * A UUID that uniquely identifies the Event. This value always remains the same from
	 * installation to installation and from restart to restart.
	 * 
	 * Create it using a random GUID/UUID generator.
	 * @return UUID The action's unique identifier.
	 */
	public UUID getUUID();
	
	/**
	 * A human readable name for the Action. 
	 * @return String Action's name.
	 */
	public String getName();
	
	/**
	 * A list of Attribute's that are describe the Event.
	 * 
	 * May include attributes that are always present with the Event,
	 * as well as some that are sometimes included with an Event.
	 * @return List of this Event's Attributes
	 */
	public List<AttributeDefinition<?>> getAttributeDefinitions();
	
}