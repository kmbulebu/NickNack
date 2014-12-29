package com.github.kmbulebu.nicknack.basicproviders.clock;

import java.util.UUID;

import com.github.kmbulebu.nicknack.core.events.impl.DayOfMonthAttributeDefinition;
import com.github.kmbulebu.nicknack.core.events.impl.DayOfWeekAttributeDefinition;
import com.github.kmbulebu.nicknack.core.events.impl.HourOfDayAttributeDefinition;
import com.github.kmbulebu.nicknack.core.events.impl.MinuteOfHourAttributeDefinition;
import com.github.kmbulebu.nicknack.core.events.impl.MonthOfYearNumericalAttributeDefinition;
import com.github.kmbulebu.nicknack.core.events.impl.SecondOfMinuteAttributeDefinition;
import com.github.kmbulebu.nicknack.core.events.impl.YearAttributeDefinition;
import com.github.kmbulebu.nicknack.core.states.BasicStateDefinition;

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
public class ClockStateDefinition extends BasicStateDefinition {

	public static UUID INSTANCE_UUID = UUID.fromString("622da195-3145-4f97-9b9c-b039bd831f9f");

	public static ClockStateDefinition INSTANCE = new ClockStateDefinition();

	public ClockStateDefinition() {
		super(INSTANCE_UUID, "Current Time", YearAttributeDefinition.INSTANCE,
				MonthOfYearNumericalAttributeDefinition.INSTANCE, DayOfMonthAttributeDefinition.INSTANCE,
				DayOfWeekAttributeDefinition.INSTANCE, HourOfDayAttributeDefinition.INSTANCE,
				MinuteOfHourAttributeDefinition.INSTANCE, SecondOfMinuteAttributeDefinition.INSTANCE);
	}

}
