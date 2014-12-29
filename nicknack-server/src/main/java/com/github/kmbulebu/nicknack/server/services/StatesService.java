package com.github.kmbulebu.nicknack.server.services;

import java.util.List;
import java.util.UUID;

import com.github.kmbulebu.nicknack.server.model.StatesResource;

public interface StatesService {
	
	public List<StatesResource> getAllStates();
	
	public List<StatesResource> getAllStatesByProvider(UUID providerUuid);
	
	public StatesResource getStates(UUID stateDefinitionUuid);

}
