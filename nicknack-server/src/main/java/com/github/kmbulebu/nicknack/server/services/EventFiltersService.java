package com.github.kmbulebu.nicknack.server.services;

import java.util.List;
import java.util.UUID;

import com.github.kmbulebu.nicknack.server.model.EventFilterResource;

public interface EventFiltersService {
	
	public List<EventFilterResource> getEventFilters(UUID planUuid);
	
	public EventFilterResource getEventFilter(UUID uuid);
	
	public void deleteEventFilter(UUID uuid);
	
	public EventFilterResource createEventFilter(UUID planUuid, EventFilterResource newEventFilter);
	
	public EventFilterResource modifyEventFilter(EventFilterResource modifiedEventFilter);

}
