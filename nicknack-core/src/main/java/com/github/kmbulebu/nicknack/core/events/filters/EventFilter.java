package com.github.kmbulebu.nicknack.core.events.filters;

import java.util.UUID;

import com.github.kmbulebu.nicknack.core.attributes.filters.AttributeFilterExpressionCollection;

/**
 * A user created Event Filter.
 * 
 * Specifies the Event the user wants to match 
 * and a collection of Attribute Filter Expressions to further
 * identify the Event.
 *
 *
 */
public interface EventFilter extends AttributeFilterExpressionCollection {
	
	/**
	 * UUID of the EventDefinition that this filter matches.
	 * @return UUID of the EventDefinition that this filter matches.
	 */
	public UUID getAppliesToEventDefinition();

}
