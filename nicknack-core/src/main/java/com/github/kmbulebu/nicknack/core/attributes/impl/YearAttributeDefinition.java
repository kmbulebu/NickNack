package com.github.kmbulebu.nicknack.core.attributes.impl;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.github.kmbulebu.nicknack.core.attributes.BasicAttributeDefinition;
import com.github.kmbulebu.nicknack.core.valuetypes.WholeNumberType;

public class YearAttributeDefinition extends BasicAttributeDefinition<WholeNumberType>{
	
	public static final YearAttributeDefinition INSTANCE = new YearAttributeDefinition();
	
	private static final int MIN_YEAR = Calendar.getInstance().get(Calendar.YEAR);
	private static final int MAX_YEAR = Calendar.getInstance().get(Calendar.YEAR) + 10;

	public YearAttributeDefinition() {
		super(UUID.fromString("9f2cdc64-8cb6-4289-a865-b6c254292913"), "Year (4 digit)", new WholeNumberType(MIN_YEAR, MAX_YEAR, 1), true);
	}
	
	public Map<String, String> getStaticValues() {
		final Map<String, String> values = new HashMap<>();
		for (int i = 0; i < 11 ; i++) {
			values.put(Integer.toString(MIN_YEAR + i), Integer.toString(MIN_YEAR + i));
		}
		return values;
	}

}
