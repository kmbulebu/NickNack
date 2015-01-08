package com.github.kmbulebu.nicknack.core.events.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import com.github.kmbulebu.nicknack.core.attributes.AttributeDefinition;
import com.github.kmbulebu.nicknack.core.attributes.impl.DayOfMonthAttributeDefinition;
import com.github.kmbulebu.nicknack.core.attributes.impl.DayOfWeekAttributeDefinition;
import com.github.kmbulebu.nicknack.core.attributes.impl.HourOfDayAttributeDefinition;
import com.github.kmbulebu.nicknack.core.attributes.impl.LongFormatDateAttributeDefinition;
import com.github.kmbulebu.nicknack.core.attributes.impl.LongFormatTimeAttributeDefinition;
import com.github.kmbulebu.nicknack.core.attributes.impl.MinuteOfHourAttributeDefinition;
import com.github.kmbulebu.nicknack.core.attributes.impl.MonthOfYearNumericalAttributeDefinition;
import com.github.kmbulebu.nicknack.core.attributes.impl.SecondOfMinuteAttributeDefinition;
import com.github.kmbulebu.nicknack.core.attributes.impl.YearAttributeDefinition;
import com.github.kmbulebu.nicknack.core.events.BasicEventDefinition;

/**
 * Basic implementation of an EventDefinition that includes timestamp information.
 * 
 * May be extended or used as-is.
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
		attributeDefinitions.add(LongFormatTimeAttributeDefinition.INSTANCE);
		attributeDefinitions.add(LongFormatDateAttributeDefinition.INSTANCE);
		attributeDefinitions.addAll(newAttributeDefinitions);
		return attributeDefinitions;
	}

}
