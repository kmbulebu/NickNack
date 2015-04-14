package com.github.kmbulebu.nicknack.core.providers;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import rx.Observable;

import com.github.kmbulebu.nicknack.core.actions.Action;
import com.github.kmbulebu.nicknack.core.actions.ActionAttributeException;
import com.github.kmbulebu.nicknack.core.actions.ActionDefinition;
import com.github.kmbulebu.nicknack.core.actions.ActionFailureException;
import com.github.kmbulebu.nicknack.core.attributes.AttributeCollection;
import com.github.kmbulebu.nicknack.core.attributes.AttributeDefinition;
import com.github.kmbulebu.nicknack.core.events.Event;
import com.github.kmbulebu.nicknack.core.events.EventDefinition;
import com.github.kmbulebu.nicknack.core.states.State;
import com.github.kmbulebu.nicknack.core.states.StateDefinition;

public interface ProviderService {
	
	public ActionDefinition getActionDefinition(UUID actionDefinitionUuid);
	
	public Collection<ActionDefinition> getActionDefinitions();
	
	public Collection<ActionDefinition> getActionDefinitionsByProviderUuid(UUID providerUuid);
	
	public EventDefinition getEventDefinition(UUID eventDefinitionUuid);
	
	public Collection<EventDefinition> getEventDefinitions();
	
	public Collection<EventDefinition> getEventDefinitionsByProviderUuid(UUID providerUuid);
	
	public StateDefinition getStateDefinition(UUID stateDefinitionUuid);
	
	public Collection<StateDefinition> getStateDefinitions();
	
	public Collection<StateDefinition> getStateDefinitionsByProviderUuid(UUID providerUuid);
	
	public AttributeDefinition<?,?> getAttributeDefinition(UUID attributeDefinitionUuid);

	public Map<UUID, Provider> getProviders();
	
	public Provider getProviderByActionDefinitionUuid(UUID actionDefinitionUuid);
	
	public Provider getProviderByEventDefinitionUuid(UUID eventDefinitionUuid);
	
	public Provider getProviderByStateDefinitionUuid(UUID stateDefinitionUuid);
	
	public AttributeCollection getProviderSettings(UUID providerUuid);
	
	public void setProviderSettings(UUID providerUuid, AttributeCollection settings);
	
	public Observable<Event> getEvents();
	
	public List<State> getStates(UUID stateDefinitionUuid);
	
	public void run(Action action) throws ActionFailureException, ActionAttributeException;
	
	public void addProvider(Provider provider);
	
	public Map<UUID, Exception> getProviderInitializationExceptions();

	public void initialize();

	public List<Object> getValueChoices(UUID providerUuid, AttributeDefinition<?, ?> attributeDefinition);
	//public <S extends AttributeDefinition<T,U>, T extends ValueType<U>, U> List<U> getValueChoices(UUID providerUuid, S attributeDefinition);

}