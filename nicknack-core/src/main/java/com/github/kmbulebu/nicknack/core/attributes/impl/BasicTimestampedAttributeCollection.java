package com.github.kmbulebu.nicknack.core.attributes.impl;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import com.github.kmbulebu.nicknack.core.attributes.AttributeCollection;
import com.github.kmbulebu.nicknack.core.attributes.AttributeDefinition;

/**
 * Basic implementation of an AttributeDefinitionCollection that includes timestamp properties.
 * 
 * Useful for when the time of an event may need preserved.
 *
 */
public class BasicTimestampedAttributeCollection implements AttributeCollection {
	
	final Map<UUID, String> attributes = new HashMap<UUID, String>();
	
	public BasicTimestampedAttributeCollection() {
		this(new Date());
	}
	
	public BasicTimestampedAttributeCollection(Date timestamp) {
		final GregorianCalendar cal = new GregorianCalendar();
		final DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.LONG);
		final DateFormat timeFormat = DateFormat.getTimeInstance(DateFormat.LONG);
		cal.setTime(timestamp);
		attributes.put(YearAttributeDefinition.INSTANCE.getUUID(), Integer.toString(cal.get(Calendar.YEAR)));
		attributes.put(MonthOfYearNumericalAttributeDefinition.INSTANCE.getUUID(), Integer.toString(cal.get(Calendar.MONTH)));
		attributes.put(DayOfMonthAttributeDefinition.INSTANCE.getUUID(), Integer.toString(cal.get(Calendar.DAY_OF_MONTH)));
		attributes.put(DayOfWeekAttributeDefinition.INSTANCE.getUUID(), cal.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault()));
		attributes.put(HourOfDayAttributeDefinition.INSTANCE.getUUID(), Integer.toString(cal.get(Calendar.HOUR_OF_DAY)));
		attributes.put(MinuteOfHourAttributeDefinition.INSTANCE.getUUID(), Integer.toString(cal.get(Calendar.MINUTE)));
		attributes.put(SecondOfMinuteAttributeDefinition.INSTANCE.getUUID(), Integer.toString(cal.get(Calendar.SECOND)));	
		attributes.put(LongFormatDateAttributeDefinition.INSTANCE.getUUID(), dateFormat.format(timestamp));
		attributes.put(LongFormatTimeAttributeDefinition.INSTANCE.getUUID(), timeFormat.format(timestamp));
	}
	
	public BasicTimestampedAttributeCollection setAttribute(UUID uuid, String value) {
		attributes.put(uuid, value);
		return this;
	}
	
	public BasicTimestampedAttributeCollection setAttribute(AttributeDefinition<?,?> attribute, String value) {
		attributes.put(attribute.getUUID(), value);
		return this;
	}
	
	public BasicTimestampedAttributeCollection setAttributes(Map<UUID, String> attributes) {
		this.attributes.putAll(attributes);
		return this;
	}

	@Override
	public Map<UUID, String> getAttributes() {
		return Collections.unmodifiableMap(attributes);
	}

}
