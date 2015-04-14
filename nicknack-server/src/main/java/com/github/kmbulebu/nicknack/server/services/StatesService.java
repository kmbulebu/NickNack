package com.github.kmbulebu.nicknack.server.services;

import java.util.List;
import java.util.UUID;

import com.github.kmbulebu.nicknack.server.restmodel.State;

public interface StatesService {

	public List<State> getAllStates();

	public List<State> getStatesByProvider(UUID providerUuid);

	public List<State> getStatesByStateDefinition(UUID uuid);

}
