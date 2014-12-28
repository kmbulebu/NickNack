package com.github.kmbulebu.nicknack.core.events.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import com.github.kmbulebu.nicknack.core.attributes.AttributeDefinition;
import com.github.kmbulebu.nicknack.core.events.BasicEventDefinition;

/**
 * Creates a new 'Time Changed' event every second, nearest to the top of the
 * second as possible.
 * 
 * 'dateTime' attribute defines the date and time the clock tick event was
 * generated.
 * 
 * @author kmbulebu
 * 
 */
public abstract class BasicTimestampedEventDefinition extends BasicEventDefinition {

	public BasicTimestampedEventDefinition(UUID uuid, String name, List<AttributeDefinition> attributeDefinitions) {
		super(uuid, name, addTimeAttributeDefs(attributeDefinitions));
	}
	
	public BasicTimestampedEventDefinition(UUID uuid, String name, AttributeDefinition... attributeDefinitions) {
		super(uuid, name, addTimeAttributeDefs(Arrays.asList(attributeDefinitions)));
	}

	private static List<AttributeDefinition> addTimeAttributeDefs(List<AttributeDefinition> newAttributeDefinitions) {
		List<AttributeDefinition> attributeDefinitions = new ArrayList<AttributeDefinition>();
		attributeDefinitions.add(YearAttributeDefinition.INSTANCE);
		attributeDefinitions.add(MonthOfYearNumericalAttributeDefinition.INSTANCE);
		attributeDefinitions.add(DayOfMonthAttributeDefinition.INSTANCE);
		attributeDefinitions.add(DayOfWeekAttributeDefinition.INSTANCE);
		attributeDefinitions.add(HourOfDayAttributeDefinition.INSTANCE);
		attributeDefinitions.add(MinuteOfHourAttributeDefinition.INSTANCE);
		attributeDefinitions.add(SecondOfMinuteAttributeDefinition.INSTANCE);
		attributeDefinitions.addAll(newAttributeDefinitions);
		return attributeDefinitions;
	}

}
