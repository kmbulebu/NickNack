package com.github.kmbulebu.nicknack.core.attributes.impl;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.UUID;

import com.github.kmbulebu.nicknack.core.attributes.BasicAttributeDefinition;
import com.github.kmbulebu.nicknack.core.valuetypes.builder.ValueTypeBuilder;
import com.github.kmbulebu.nicknack.core.valuetypes.impl.StaticValueChoices;
import com.github.kmbulebu.nicknack.core.valuetypes.impl.text.Text;

public class DayOfWeekAttributeDefinition extends BasicAttributeDefinition<Text, String> {
	
	public static final DayOfWeekAttributeDefinition INSTANCE = new DayOfWeekAttributeDefinition();

	public DayOfWeekAttributeDefinition() {
		super(UUID.fromString("47a08eac-9e3a-4c5a-b354-c3354e8f2a67"), 
		"Day of Week",
		ValueTypeBuilder.text().build(),
		new StaticValueChoices<String>(GregorianCalendar.getInstance().getDisplayNames(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault()).keySet().toArray(new String[7])), 
		true,
		false);
	}
	

}
