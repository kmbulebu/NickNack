package com.oakcity.nicknack.basicproviders.clock;

import java.util.UUID;

import com.oakcity.nicknack.core.events.BasicAttributeDefinition;
import com.oakcity.nicknack.core.units.IntegerUnit;

public class MonthOfYearNumericalAttributeDefinition extends BasicAttributeDefinition{
	
	public static final MonthOfYearNumericalAttributeDefinition INSTANCE = new MonthOfYearNumericalAttributeDefinition();

	public MonthOfYearNumericalAttributeDefinition() {
		super(UUID.fromString("f9f4ce53-03a6-4d47-81d1-702663877a94"), "Month of Year (Numerical)", IntegerUnit.INSTANCE, true);
	}

}
