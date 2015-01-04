package com.github.kmbulebu.nicknack.server.services;

import java.util.List;
import java.util.UUID;

import com.github.kmbulebu.nicknack.server.model.EventFilterResource;
import com.github.kmbulebu.nicknack.server.services.exceptions.EventFilterNotFoundException;
import com.github.kmbulebu.nicknack.server.services.exceptions.PlanNotFoundException;

public interface EventFiltersService {
	
	public List<EventFilterResource> getEventFilters(UUID planUuid) throws PlanNotFoundException;
	
	public EventFilterResource getEventFilter(UUID uuid) throws EventFilterNotFoundException;
	
	public void deleteEventFilter(UUID uuid) throws EventFilterNotFoundException;
	
	public EventFilterResource createEventFilter(UUID planUuid, EventFilterResource newEventFilter) throws PlanNotFoundException;
	
	public EventFilterResource modifyEventFilter(EventFilterResource modifiedEventFilter) throws EventFilterNotFoundException;

}
