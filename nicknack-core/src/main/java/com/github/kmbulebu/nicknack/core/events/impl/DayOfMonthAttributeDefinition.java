package com.github.kmbulebu.nicknack.core.events.impl;

import java.util.UUID;

import com.github.kmbulebu.nicknack.core.attributes.BasicAttributeDefinition;
import com.github.kmbulebu.nicknack.core.units.IntegerUnit;

public class DayOfMonthAttributeDefinition extends BasicAttributeDefinition{
	
	public static final DayOfMonthAttributeDefinition INSTANCE = new DayOfMonthAttributeDefinition();

	public DayOfMonthAttributeDefinition() {
		super(UUID.fromString("99dca082-5d49-40ab-bbe2-853b303f3192"), "Day of Month", IntegerUnit.INSTANCE, true);
	}

}
