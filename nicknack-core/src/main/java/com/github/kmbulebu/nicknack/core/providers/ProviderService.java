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

public interface ProviderService {

	public Map<UUID, ActionDefinition> getActionDefinitions();

	public Map<UUID, EventDefinition> getEventDefinitions();
	
	public Map<UUID, Provider> getProviders();
	
	public Provider getProviderByActionDefinitionUuid(UUID actionDefinitionUuid);
	
	public Provider getProviderByEventDefinitionUuid(UUID eventDefinitionUuid);
	
	public Observable<Event> getEvents();
	
	public void run(Action action) throws ActionFailureException, ActionParameterException;
	
	public List<Exception> addProvider(Provider provider);

}