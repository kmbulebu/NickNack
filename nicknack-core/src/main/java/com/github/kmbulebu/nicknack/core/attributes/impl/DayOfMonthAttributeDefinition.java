package com.github.kmbulebu.nicknack.core.attributes.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.github.kmbulebu.nicknack.core.attributes.BasicAttributeDefinition;
import com.github.kmbulebu.nicknack.core.valuetypes.WholeNumberType;

public class DayOfMonthAttributeDefinition extends BasicAttributeDefinition<WholeNumberType>{
	
	public static final DayOfMonthAttributeDefinition INSTANCE = new DayOfMonthAttributeDefinition();

	public DayOfMonthAttributeDefinition() {
		super(UUID.fromString("99dca082-5d49-40ab-bbe2-853b303f3192"), "Day of Month", new WholeNumberType(0, 31, 1), true);
	}
	
	public Map<String, String> getStaticValues() {
		final Map<String, String> values = new HashMap<>();
		for (int i = 1; i < 32 ; i++) {
			values.put(Integer.toString(i), Integer.toString(i));
		}
		return values;
	}

}
