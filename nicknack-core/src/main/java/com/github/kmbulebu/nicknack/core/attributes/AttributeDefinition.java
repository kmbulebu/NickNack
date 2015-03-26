package com.github.kmbulebu.nicknack.core.attributes;

import java.util.UUID;

import com.github.kmbulebu.nicknack.core.valuetypes.ValueChoices;
import com.github.kmbulebu.nicknack.core.valuetypes.ValueType;

/**
 * Defines an attribute to be associated with an Event, State, or Action.
 * 
 *
 */
public interface AttributeDefinition<T extends ValueType<U>, U> {
	
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
	
	/**
	 * 
	 * @return True if this attribute may have multiple, simultaneous values. False if only one.
	 */
	public boolean isMultiValue();
	
	/**
	 * The type of values for this attribute.
	 * 
	 * @return Defines the values of this attribute.
	 */
	public T getValueType();
	
	/**
	 * Human readable help text to further explain the purpose of the attribute.
	 * @return String Description
	 */
	public String getDescription();
	
	/**
	 * 
	 * @return Null if the attribute accepts any value entered by the user. Or a class that will provide a list of possible values.
	 */
	public ValueChoices<U> getValueChoices();
	
	
}