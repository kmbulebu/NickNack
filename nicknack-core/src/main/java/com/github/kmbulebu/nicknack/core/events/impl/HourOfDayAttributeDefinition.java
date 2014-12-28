package com.github.kmbulebu.nicknack.core.events.impl;

import java.util.UUID;

import com.github.kmbulebu.nicknack.core.attributes.BasicAttributeDefinition;
import com.github.kmbulebu.nicknack.core.units.IntegerUnit;

public class HourOfDayAttributeDefinition extends BasicAttributeDefinition{
	
	public static final HourOfDayAttributeDefinition INSTANCE = new HourOfDayAttributeDefinition();

	public HourOfDayAttributeDefinition() {
		super(UUID.fromString("b00abf0d-7bda-4c44-a7a1-55fe15ccc6b6"), "Hour of Day", IntegerUnit.INSTANCE, true);
	}

}
