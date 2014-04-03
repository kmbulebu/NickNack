package com.oakcity.nicknack.events;

import java.util.List;

import com.oakcity.nicknack.events.Event.EventDefinition;

/**
 * User created.
 * @author kmbulebu
 *
 * @param <T>
 */
public interface EventFilterOperator {

	public List<AttributeFilterSettings<?>> getAttributeFilterSettings();

	public boolean matches(Event aThis);
	
	public EventDefinition appliesTo();
	
}
