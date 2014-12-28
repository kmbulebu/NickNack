package com.github.kmbulebu.nicknack.server.services;

import java.util.List;
import java.util.UUID;

import com.github.kmbulebu.nicknack.server.model.StateFilterResource;

public interface StateFiltersService {
	
	public List<StateFilterResource> getStateFilters(UUID planUuid);
	
	public StateFilterResource getStateFilter(UUID uuid);
	
	public void deleteStateFilter(UUID uuid);
	
	public StateFilterResource createStateFilter(UUID planUuid, StateFilterResource newStateFilter);
	
	public StateFilterResource modifyStateFilter(StateFilterResource modifiedStateFilter);

}
