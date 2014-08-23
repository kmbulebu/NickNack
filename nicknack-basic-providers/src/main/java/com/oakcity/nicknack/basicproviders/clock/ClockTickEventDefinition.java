package com.oakcity.nicknack.basicproviders.clock;

import java.util.UUID;

import com.oakcity.nicknack.core.events.BasicEventDefinition;

/**
 * Creates a new 'Time Changed' event every second, nearest to the top of the second as possible.
 * 
 * 'dateTime' attribute defines the date and time the clock tick event was generated.
 * 
 * @author kmbulebu
 *
 */
public class ClockTickEventDefinition extends BasicEventDefinition {
	
	public static UUID INSTANCE_UUID = UUID.fromString("541ce254-f10e-11e3-bac5-a75fc2793419");

	public static ClockTickEventDefinition INSTANCE = new ClockTickEventDefinition();
	
	public ClockTickEventDefinition() {
		super(INSTANCE_UUID, 
				"Time Changed", 
				YearAttributeDefinition.INSTANCE,
				MonthOfYearNumericalAttributeDefinition.INSTANCE,
				DayOfMonthAttributeDefinition.INSTANCE,
				DayOfWeekAttributeDefinition.INSTANCE,
				HourOfDayAttributeDefinition.INSTANCE,
				MinuteOfHourAttributeDefinition.INSTANCE,
				SecondOfMinuteAttributeDefinition.INSTANCE);
	}
	
}
