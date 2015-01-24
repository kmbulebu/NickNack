package com.github.kmbulebu.nicknack.core.actions;

import java.util.List;
import java.util.UUID;

import com.github.kmbulebu.nicknack.core.attributes.AttributeDefinition;

/**
 * Define's an Action that a user may perform. 
 *
 */
public interface ActionDefinition {
	
	/**
	 * A UUID that uniquely identifies the Action. This value always remains the same from
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
	 * Human readable help text to further explain the purpose of the Action.
	 * @return String Description
	 */
	public String getDescription();
	
	/**
	 * A list of Attribute's that are necessary for the execution of this Action.
	 * 
	 * May include both required and optional attributes.
	 * @return List of this Action's Attributes
	 */
	public List<AttributeDefinition<?>> getAttributeDefinitions();

}