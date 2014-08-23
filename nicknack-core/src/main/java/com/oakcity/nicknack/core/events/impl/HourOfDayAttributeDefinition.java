package com.oakcity.nicknack.core.events.impl;

import java.util.UUID;

import com.oakcity.nicknack.core.events.BasicAttributeDefinition;
import com.oakcity.nicknack.core.units.IntegerUnit;

public class HourOfDayAttributeDefinition extends BasicAttributeDefinition{
	
	public static final HourOfDayAttributeDefinition INSTANCE = new HourOfDayAttributeDefinition();

	public HourOfDayAttributeDefinition() {
		super(UUID.fromString("b00abf0d-7bda-4c44-a7a1-55fe15ccc6b6"), "Hour of Day", IntegerUnit.INSTANCE, true);
	}

}
