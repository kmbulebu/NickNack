package com.oakcity.nicknack.plans;

import java.util.List;
import java.util.UUID;

import com.oakcity.nicknack.actions.Action;
import com.oakcity.nicknack.events.filters.EventFilter;

public interface Plan {
	
	public UUID getUUID();
	
	// Needed? We can probably generate a good name.
	public String name();
	
	public List<EventFilter> getEventFilters();
	
	public Action getAction();

}
