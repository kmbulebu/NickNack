package com.github.kmbulebu.nicknack.core.attributes.impl;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.github.kmbulebu.nicknack.core.attributes.BasicAttributeDefinition;
import com.github.kmbulebu.nicknack.core.units.StringUnit;

public class YearAttributeDefinition extends BasicAttributeDefinition{
	
	public static final YearAttributeDefinition INSTANCE = new YearAttributeDefinition();

	public YearAttributeDefinition() {
		super(UUID.fromString("9f2cdc64-8cb6-4289-a865-b6c254292913"), "Year (4 digit)", StringUnit.INSTANCE, true);
	}
	
	public Map<String, String> getStaticValues() {
		final Map<String, String> values = new HashMap<>();
		int year = Calendar.getInstance().get(Calendar.YEAR);
		for (int i = 0; i < 11 ; i++) {
			values.put(Integer.toString(year + i), Integer.toString(year + i));
		}
		return values;
	}

}
