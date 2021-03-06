package com.github.kmbulebu.nicknack.core.attributes.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.github.kmbulebu.nicknack.core.attributes.BasicAttributeDefinition;
import com.github.kmbulebu.nicknack.core.units.IntegerUnit;

public class MonthOfYearNumericalAttributeDefinition extends BasicAttributeDefinition{
	
	public static final MonthOfYearNumericalAttributeDefinition INSTANCE = new MonthOfYearNumericalAttributeDefinition();

	public MonthOfYearNumericalAttributeDefinition() {
		super(UUID.fromString("f9f4ce53-03a6-4d47-81d1-702663877a94"), "Month of Year (Numerical)", IntegerUnit.INSTANCE, true);
	}
	
	public Map<String, String> getStaticValues() {
		final Map<String, String> values = new HashMap<>();
		for (int i = 1; i < 13 ; i++) {
			values.put(Integer.toString(i), Integer.toString(i));
		}
		return values;
	}

}
