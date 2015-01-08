package com.github.kmbulebu.nicknack.core.attributes;

import java.util.Map;
import java.util.UUID;

/**
 * A collection of Attributes that help to define an Action, State Filter, or Event Filter
 *
 */
public interface AttributeCollection {
	
	/**
	 * Map of attributes where the key is AttributeDefinition's UUID and the value is the attribute value.
	 * 
	 * @return Map of attributes where the key is AttributeDefinition's UUID and the value is the attribute value.
	 */
	public Map<UUID, String> getAttributes();

}
