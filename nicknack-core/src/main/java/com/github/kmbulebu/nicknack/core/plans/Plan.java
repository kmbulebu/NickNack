package com.github.kmbulebu.nicknack.core.plans;

import java.util.List;
import java.util.UUID;

import com.github.kmbulebu.nicknack.core.actions.Action;
import com.github.kmbulebu.nicknack.core.events.filters.EventFilter;

public interface Plan {
	
	public UUID getUUID();
	
	// Needed? We can probably generate a good name.
	public String getName();
	
	public List<EventFilter> getEventFilters();
	
	public List<Action> getActions();

}
