package com.github.kmbulebu.nicknack.core.events;

import java.util.Date;

import com.github.kmbulebu.nicknack.core.attributes.AttributeCollection;



/**
 * Provider generated Event.
 *
 */
public interface Event extends AttributeCollection {
	
	// Are we adequately tying Events to their definition?
	
	// TODO Switch to just UUID?
	/**
	 * EventDefinition that describes this Event.
	 * @return EventDefinition that describes this Event.
	 */
	public EventDefinition getEventDefinition();
	
	/**
	 * Time at which this Event was created by the provider.
	 * @return Date and Time at which this Event was created by the provider.
	 */
	public Date getCreated();
	
}
