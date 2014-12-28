package com.github.kmbulebu.nicknack.core.events;

import java.util.Date;

import com.github.kmbulebu.nicknack.core.attributes.AttributeCollection;



/**
 * An event.
 * @author Kevin Bulebush
 *
 */
public interface Event extends AttributeCollection {
	
	// Are we adequately tying Events to their definition?
	
	// TODO Switch to just UUID?
	public EventDefinition getEventDefinition();
	
	public Date getCreated();
	
}
