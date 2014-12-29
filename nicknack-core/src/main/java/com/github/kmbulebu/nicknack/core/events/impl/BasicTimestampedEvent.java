package com.github.kmbulebu.nicknack.core.events.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.github.kmbulebu.nicknack.core.attributes.impl.BasicTimestampedAttributeCollection;
import com.github.kmbulebu.nicknack.core.events.Event;
import com.github.kmbulebu.nicknack.core.events.EventDefinition;

public class BasicTimestampedEvent extends BasicTimestampedAttributeCollection implements Event  {
	
	final Map<UUID, String> attributes = new HashMap<UUID, String>();
	
	final EventDefinition eventDefinition;
	
	final Date created;
	
	public BasicTimestampedEvent(EventDefinition eventDefinition) {
		this(eventDefinition, new Date());
	}
	
	public BasicTimestampedEvent(EventDefinition eventDefinition, Date timestamp) {
		super(timestamp);
		this.created = timestamp;
		this.eventDefinition = eventDefinition;
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
