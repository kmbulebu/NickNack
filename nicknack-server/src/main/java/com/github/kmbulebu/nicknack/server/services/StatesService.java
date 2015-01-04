package com.github.kmbulebu.nicknack.server.services;

import java.util.List;
import java.util.UUID;

import com.github.kmbulebu.nicknack.server.model.StatesResource;
import com.github.kmbulebu.nicknack.server.services.exceptions.ProviderNotFoundException;
import com.github.kmbulebu.nicknack.server.services.exceptions.StateDefinitionNotFoundException;

public interface StatesService {
	
	public List<StatesResource> getAllStates();
	
	public List<StatesResource> getAllStatesByProvider(UUID providerUuid) throws ProviderNotFoundException;
	
	public StatesResource getStates(UUID stateDefinitionUuid) throws StateDefinitionNotFoundException, ProviderNotFoundException;

}
