package com.oakcity.nicknack.providers.ssh;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.configuration.Configuration;

import com.oakcity.nicknack.core.actions.Action;
import com.oakcity.nicknack.core.actions.ActionDefinition;
import com.oakcity.nicknack.core.actions.ActionFailureException;
import com.oakcity.nicknack.core.actions.ActionParameterException;
import com.oakcity.nicknack.core.events.EventDefinition;
import com.oakcity.nicknack.core.providers.OnEventListener;
import com.oakcity.nicknack.core.providers.Provider;

public class SshProvider implements Provider {
	
	public static final UUID PROVIDER_UUID = UUID.fromString("af5f4a6c-699b-4ee9-a11c-898e502324b8");
	
	private final Map<UUID, SshActionDefinition> actionDefinitions = new HashMap<>();

	@Override
	public UUID getUuid() {
		return PROVIDER_UUID;
	}

	@Override
	public String getName() {
		return "SSH";
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

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Collection<ActionDefinition> getActionDefinitions() {
		return (Collection) actionDefinitions.values();
	}

	@Override
	public Map<String, String> getAttributeDefinitionValues(UUID eventDefinitionUuid, UUID attributeDefinitionUuid) {
		return null;
	}

	@Override
	public void run(Action action) throws ActionFailureException, ActionParameterException {
		// Look it up.
		final SshActionDefinition actionDef = actionDefinitions.get(action.getAppliesToActionDefinition());
		if (actionDef == null) {
			throw new ActionFailureException("Action is not provided by the SSH provider. Please open a bug.");
		}
		
		actionDef.run(action);
	}

	@Override
	public void init(Configuration configuration, OnEventListener onEventListener) throws Exception {
		actionDefinitions.put(ExecuteShellCommandActionDefinition.DEF_UUID, new ExecuteShellCommandActionDefinition());
	}

}
