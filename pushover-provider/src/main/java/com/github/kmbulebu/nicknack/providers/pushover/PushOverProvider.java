package com.github.kmbulebu.nicknack.providers.pushover;


import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.configuration.Configuration;

import com.github.kmbulebu.nicknack.core.actions.Action;
import com.github.kmbulebu.nicknack.core.actions.ActionDefinition;
import com.github.kmbulebu.nicknack.core.actions.ActionFailureException;
import com.github.kmbulebu.nicknack.core.actions.ActionParameterException;
import com.github.kmbulebu.nicknack.core.events.EventDefinition;
import com.github.kmbulebu.nicknack.core.providers.OnEventListener;
import com.github.kmbulebu.nicknack.core.providers.Provider;
import com.github.kmbulebu.nicknack.core.states.State;
import com.github.kmbulebu.nicknack.core.states.StateDefinition;

public class PushOverProvider implements Provider {
	
	public static final UUID PROVIDER_UUID = UUID.fromString("ec72c198-1ee8-4fd6-be0c-c10fd365f95a");
	
	private final Map<UUID, AbstractPushMessageActionDefinition> actionDefinitions = new HashMap<>();

	@Override
	public UUID getUuid() {
		return PROVIDER_UUID;
	}

	@Override
	public String getName() {
		return "Pushover";
	}

	@Override
	public String getAuthor() {
		return "NickNack";
	}

	@Override
	public int getVersion() {
		return 0;
	}

	@Override	
	public Collection<EventDefinition> getEventDefinitions() {
		return Collections.emptyList();
	}
	
	@Override
	public Collection<StateDefinition> getStateDefinitions() {
		return Collections.emptyList();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Collection<ActionDefinition> getActionDefinitions() {
		return (Collection) actionDefinitions.values();
	}

	@Override
	public Map<String, String> getAttributeDefinitionValues(UUID attributeDefinitionUuid) {
		return null;
	}

	@Override
	public void run(Action action) throws ActionFailureException, ActionParameterException {
		// Look it up.
		final AbstractPushMessageActionDefinition actionDef = actionDefinitions.get(action.getAppliesToActionDefinition());
		if (actionDef == null) {
			throw new ActionFailureException("Action is not provided by the PushOver provider. Please open a bug.");
		}
		
		actionDef.run(action);
	}

	@Override
	public void init(Configuration configuration, OnEventListener onEventListener) throws Exception {
		actionDefinitions.put(PushMessageActionDefinition.DEF_UUID, new PushMessageActionDefinition());
	}
	
	@Override
	public List<State> getStates(UUID stateDefinitionUuid) {
		return Collections.emptyList();
	}

}
