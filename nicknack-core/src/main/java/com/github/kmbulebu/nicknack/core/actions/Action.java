package com.github.kmbulebu.nicknack.core.actions;

import java.util.UUID;

import com.github.kmbulebu.nicknack.core.attributes.AttributeCollection;

/**
 * A user created Action. 
 * 
 */
public interface Action extends AttributeCollection {
	
	/**
	 * The ActionDefinition that describes this Action.
	 * @return UUID The UUID of the ActionDefinition
	 */
	public UUID getAppliesToActionDefinition();

}
