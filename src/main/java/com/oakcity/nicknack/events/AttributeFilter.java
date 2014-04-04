package com.oakcity.nicknack.events;

import com.oakcity.nicknack.Unit;
import com.oakcity.nicknack.events.Event.Attribute;
import com.oakcity.nicknack.events.Event.AttributeDefinition;

public interface AttributeFilter<AttributeType extends AttributeDefinition<UnitType, ValueType>, UnitType extends Unit<ValueType>, ValueType> {

	public boolean match(Attribute<UnitType, ValueType> attribute, AttributeFilterSettings<ValueType> settings);
	
	public String getDescription();
	
	public Class<AttributeType> appliesTo();
	
	public AttributeFilterSettings<ValueType> newFilterSettings();
	
	// TODO Do we need a static UUID here to identify in web services?
	
}
