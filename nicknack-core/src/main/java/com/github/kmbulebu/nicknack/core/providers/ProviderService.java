package com.github.kmbulebu.nicknack.core.providers;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import rx.Observable;

import com.github.kmbulebu.nicknack.core.actions.Action;
import com.github.kmbulebu.nicknack.core.actions.ActionDefinition;
import com.github.kmbulebu.nicknack.core.actions.ActionFailureException;
import com.github.kmbulebu.nicknack.core.actions.ActionParameterException;
import com.github.kmbulebu.nicknack.core.events.Event;
import com.github.kmbulebu.nicknack.core.events.EventDefinition;
import com.github.kmbulebu.nicknack.core.states.StateDefinition;

public interface ProviderService {

	public Map<UUID, ActionDefinition> getActionDefinitions();

	public Map<UUID, EventDefinition> getEventDefinitions();
	
	public Map<UUID, StateDefinition> getStateDefinitions();
	
	public Map<UUID, Provider> getProviders();
	
	public Provider getProviderByActionDefinitionUuid(UUID actionDefinitionUuid);
	
	public Provider getProviderByEventDefinitionUuid(UUID eventDefinitionUuid);
	
	public Provider getProviderByStateDefinitionUuid(UUID stateDefinitionUuid);
	
	public Observable<Event> getEvents();
	
	public void run(Action action) throws ActionFailureException, ActionParameterException;
	
	public List<Exception> addProvider(Provider provider);
	
	public Map<String, List<?>> getProviderSettings(UUID providerUuid);
	
	public boolean isProviderSettingsComplete(UUID providerUuid);
	
	public boolean isProviderEnabled(UUID providerUuid);
	
	public Map<String, List<String>> getProviderSettingsErrors(UUID providerUuid);
	
	public void setProviderSettings(UUID providerUuid, Map<String, List<?>> settings, boolean disabled);
}