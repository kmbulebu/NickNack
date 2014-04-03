package com.oakcity.nicknack;

import java.util.List;

import com.oakcity.nicknack.Event.EventDefinition;

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
