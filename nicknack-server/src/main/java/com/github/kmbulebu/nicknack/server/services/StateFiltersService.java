package com.github.kmbulebu.nicknack.server.services;

import java.util.List;
import java.util.UUID;

import com.github.kmbulebu.nicknack.server.model.StateFilterResource;
import com.github.kmbulebu.nicknack.server.services.exceptions.PlanNotFoundException;
import com.github.kmbulebu.nicknack.server.services.exceptions.StateFilterNotFoundException;

public interface StateFiltersService {
	
	public List<StateFilterResource> getStateFilters(UUID planUuid) throws PlanNotFoundException;
	
	public StateFilterResource getStateFilter(UUID uuid) throws StateFilterNotFoundException;
	
	public void deleteStateFilter(UUID uuid) throws StateFilterNotFoundException;
	
	public StateFilterResource createStateFilter(UUID planUuid, StateFilterResource newStateFilter) throws PlanNotFoundException;
	
	public StateFilterResource modifyStateFilter(StateFilterResource modifiedStateFilter) throws StateFilterNotFoundException;

}
