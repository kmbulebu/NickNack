package com.oakcity.nicknack.core.providers;

import java.util.Map;
import java.util.UUID;

import rx.Observable;

import com.oakcity.nicknack.core.actions.Action;
import com.oakcity.nicknack.core.actions.ActionDefinition;
import com.oakcity.nicknack.core.actions.ActionFailureException;
import com.oakcity.nicknack.core.actions.ActionParameterException;
import com.oakcity.nicknack.core.events.Event;
import com.oakcity.nicknack.core.events.EventDefinition;

public interface ProviderService {

	public Map<UUID, ActionDefinition> getActionDefinitions();

	public Map<UUID, EventDefinition> getEventDefinitions();
	
	public Map<UUID, Provider> getProviders();
	
	public Provider getProviderByActionDefinitionUuid(UUID actionDefinitionUuid);
	
	public Provider getProviderByEventDefinitionUuid(UUID eventDefinitionUuid);
	
	public Observable<Event> getEvents();
	
	public void run(Action action) throws ActionFailureException, ActionParameterException;

}