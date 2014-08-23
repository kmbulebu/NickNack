package com.oakcity.nicknack.basicproviders.clock;

import java.util.UUID;

import com.oakcity.nicknack.core.events.BasicAttributeDefinition;
import com.oakcity.nicknack.core.units.IntegerUnit;

public class DayOfMonthAttributeDefinition extends BasicAttributeDefinition{
	
	public static final DayOfMonthAttributeDefinition INSTANCE = new DayOfMonthAttributeDefinition();

	public DayOfMonthAttributeDefinition() {
		super(UUID.fromString("99dca082-5d49-40ab-bbe2-853b303f3192"), "Day of Month", IntegerUnit.INSTANCE, true);
	}

}
