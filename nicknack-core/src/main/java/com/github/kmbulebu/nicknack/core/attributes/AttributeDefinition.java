package com.github.kmbulebu.nicknack.core.attributes;

import java.util.UUID;

import com.github.kmbulebu.nicknack.core.valuetypes.ValueType;

/**
 * Defines an attribute to be associated with an Event, State, or Action.
 * 
 *
 */
public interface AttributeDefinition<T extends ValueType<?>> {
	
	/**
	 * A UUID that uniquely identifies the attribute. This value always remains the same from
	 * installation to installation and from restart to restart.
	 * 
	 * Create it using a random GUID/UUID generator.
	 * @return UUID The attribute's unique identifier.
	 */
	public UUID getUUID();
	
	/**
	 * A human readable name for the attribute. 
	 * @return String Attribute's name.
	 */
	public String getName();
	
	/**
	 * Defines if this attribute is required by the associated Event, State, or Action.
	 * 
	 * For Actions, this attribute must be provided to work.
	 * For Events and States, isRequired indicates if the attribute will always exist. 
	 * @return boolean True if attribute is required, false otherwise.
	 */
	public boolean isRequired();
	
	public T getValueType();
	
	/**
	 * Human readable help text to further explain the purpose of the attribute.
	 * @return String Description
	 */
	public String getDescription();
	
	
	
	
}