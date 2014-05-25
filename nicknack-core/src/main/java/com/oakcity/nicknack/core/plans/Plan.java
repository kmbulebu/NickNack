package com.oakcity.nicknack.core.plans;

import java.util.List;
import java.util.UUID;

import com.oakcity.nicknack.core.actions.Action;
import com.oakcity.nicknack.core.events.filters.EventFilter;

public interface Plan {
	
	public UUID getUUID();
	
	// Needed? We can probably generate a good name.
	public String getName();
	
	public List<EventFilter> getEventFilters();
	
	public Action getAction();

}
