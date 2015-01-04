package com.github.kmbulebu.nicknack.core.attributes.impl;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import com.github.kmbulebu.nicknack.core.attributes.BasicAttributeDefinition;
import com.github.kmbulebu.nicknack.core.units.StringUnit;

public class DayOfWeekAttributeDefinition extends BasicAttributeDefinition{
	
	public static final DayOfWeekAttributeDefinition INSTANCE = new DayOfWeekAttributeDefinition();

	public DayOfWeekAttributeDefinition() {
		super(UUID.fromString("47a08eac-9e3a-4c5a-b354-c3354e8f2a67"), "Day of Week", StringUnit.INSTANCE, true);
	}
	
	public Map<String, String> getStaticValues() {
		final Map<String, String> values = new HashMap<>();
		for (String day : GregorianCalendar.getInstance().getDisplayNames(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault()).keySet()) {
			values.put(day, day);
		}
		return values;
	}

}
