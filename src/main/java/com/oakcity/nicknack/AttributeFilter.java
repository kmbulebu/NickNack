package com.oakcity.nicknack;

import com.oakcity.nicknack.Event.Attribute;
import com.oakcity.nicknack.Event.AttributeDefinition;
import com.oakcity.nicknack.Event.AttributeDefinition.Unit;

public interface AttributeFilter<AttributeType extends AttributeDefinition<UnitType, ValueType>, UnitType extends Unit<ValueType>, ValueType> {

	public boolean match(Attribute<UnitType, ValueType> attribute, AttributeFilterSettings<ValueType> settings);
	
	public String getDescription();
	
	public Class<AttributeType> appliesTo();
	
	public AttributeFilterSettings<ValueType> newFilterSettings();
	
	// TODO Do we need a static UUID here to identify in web services?
	
}
