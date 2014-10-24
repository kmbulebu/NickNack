package com.github.kmbulebu.nicknack.core.events.impl;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import com.github.kmbulebu.nicknack.core.events.AttributeDefinition;
import com.github.kmbulebu.nicknack.core.events.Event;
import com.github.kmbulebu.nicknack.core.events.EventDefinition;

public class BasicTimestampedEvent implements Event {
	
	final Map<UUID, String> attributes = new HashMap<UUID, String>();
	
	final EventDefinition eventDefinition;
	
	final Date created;
	
	public BasicTimestampedEvent(EventDefinition eventDefinition) {
		this(eventDefinition, new Date());
	}
	
	public BasicTimestampedEvent(EventDefinition eventDefinition, Date timestamp) {
		this.created = timestamp;
		this.eventDefinition = eventDefinition;
		final GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(timestamp);
		attributes.put(YearAttributeDefinition.INSTANCE.getUUID(), Integer.toString(cal.get(Calendar.YEAR)));
		attributes.put(MonthOfYearNumericalAttributeDefinition.INSTANCE.getUUID(), Integer.toString(cal.get(Calendar.MONTH)));
		attributes.put(DayOfMonthAttributeDefinition.INSTANCE.getUUID(), Integer.toString(cal.get(Calendar.DAY_OF_MONTH)));
		attributes.put(DayOfWeekAttributeDefinition.INSTANCE.getUUID(), cal.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault()));
		attributes.put(HourOfDayAttributeDefinition.INSTANCE.getUUID(), Integer.toString(cal.get(Calendar.HOUR_OF_DAY)));
		attributes.put(MinuteOfHourAttributeDefinition.INSTANCE.getUUID(), Integer.toString(cal.get(Calendar.MINUTE)));
		attributes.put(SecondOfMinuteAttributeDefinition.INSTANCE.getUUID(), Integer.toString(cal.get(Calendar.SECOND)));	
	}
	
	public BasicTimestampedEvent setAttribute(UUID uuid, String value) {
		attributes.put(uuid, value);
		return this;
	}
	
	public BasicTimestampedEvent setAttribute(AttributeDefinition attribute, String value) {
		attributes.put(attribute.getUUID(), value);
		return this;
	}
	
	public BasicTimestampedEvent setAttributes(Map<UUID, String> attributes) {
		this.attributes.putAll(attributes);
		return this;
	}

	@Override
	public Map<UUID, String> getAttributes() {
		return Collections.unmodifiableMap(attributes);
	}

	@Override
	public EventDefinition getEventDefinition() {
		return eventDefinition;
	}

	@Override
	public Date getCreated() {
		return created;
	}

}
