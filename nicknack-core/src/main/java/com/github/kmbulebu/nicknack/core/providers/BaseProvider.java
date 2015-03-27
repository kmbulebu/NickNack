package com.github.kmbulebu.nicknack.core.providers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.github.kmbulebu.nicknack.core.actions.ActionDefinition;
import com.github.kmbulebu.nicknack.core.attributes.AttributeCollection;
import com.github.kmbulebu.nicknack.core.attributes.AttributeDefinition;
import com.github.kmbulebu.nicknack.core.events.EventDefinition;
import com.github.kmbulebu.nicknack.core.states.State;
import com.github.kmbulebu.nicknack.core.states.StateDefinition;

public abstract class BaseProvider implements Provider {
	
	private final UUID uuid;
	private final String name;
	private final String author;
	private final int version;
	
	private List<EventDefinition> eventDefinitions = null;
	private Map<UUID, ActionDefinition> actionDefinitions = null;
	private Map<UUID, StateDefinition> stateDefinitions = null;
	private final List<AttributeDefinition<?, ?>> settingDefinitions;

	public BaseProvider(UUID uuid, String name, String author, int version, List<? extends AttributeDefinition<?,?>> settingDefinitions) {
		super();
		this.uuid = uuid;
		this.name = name;
		this.author = author;
		this.version = version;
		this.settingDefinitions = Collections.unmodifiableList(settingDefinitions);
	}
	
	public BaseProvider(UUID uuid, String name, String author, int version, AttributeDefinition<?,?>... settingDefinitions) {
		this(uuid, name, author, version, Arrays.asList(settingDefinitions));
	}

	@Override
	public final UUID getUuid() {
		return uuid;
	}

	@Override
	public final String getName() {
		return name;
	}

	@Override
	public final String getAuthor() {
		return author;
	}

	@Override
	public final int getVersion() {
		return version;
	}

	@Override
	public final List<AttributeDefinition<?, ?>> getSettingDefinitions() {
		if (settingDefinitions == null) {
			return Collections.emptyList(); 
		} else {
			return Collections.unmodifiableList(settingDefinitions);
		}
	}

	@Override
	public final Collection<EventDefinition> getEventDefinitions() {
		if (eventDefinitions == null) {
			return Collections.emptyList();
		} else {
			return Collections.unmodifiableCollection(eventDefinitions);
		}
	}

	@Override
	public final Collection<StateDefinition> getStateDefinitions() {
		if (stateDefinitions == null) {
			return Collections.emptyList();
		} else {
			return Collections.unmodifiableCollection(stateDefinitions.values());
		}
	}

	@Override
	public final Collection<ActionDefinition> getActionDefinitions() {
		if (actionDefinitions == null) {
			return Collections.emptyList();
		} else {
			return Collections.unmodifiableCollection(actionDefinitions.values());
		}
	}

	@Override
	public final List<State> getStates(UUID stateDefinitionUuid) {
		if (stateDefinitions == null || stateDefinitionUuid == null) {
			return Collections.emptyList();
		} else {
			final StateDefinition stateDefinition = stateDefinitions.get(stateDefinitionUuid);
			if (stateDefinition == null) {
				return Collections.emptyList();
			} else {
				return Collections.unmodifiableList(getStates(stateDefinition));
			}
		}
	}
	
	protected abstract List<State> getStates(StateDefinition stateDefinition);
	
	@Override
	public void init(AttributeCollection settings, OnEventListener onEventListener) throws BadConfigurationException, ProviderFailureException {
		actionDefinitions = new HashMap<>();
		eventDefinitions = new ArrayList<>();
		stateDefinitions = new HashMap<>();
	}

	@Override
	public void shutdown() throws Exception {
		actionDefinitions = null;
		eventDefinitions = null;
		stateDefinitions = null;
	}
	
	protected void addEventDefinition(EventDefinition eventDefinition) {
		if (eventDefinitions == null) {
			throw new IllegalStateException("Must call super.init() first in init().");
		} else {
			eventDefinitions.add(eventDefinition);
		}
	}
	
	protected void addStateDefinition(StateDefinition stateDefinition) {
		if (stateDefinitions == null) {
			throw new IllegalStateException("Must call super.init() first in init().");
		} else {
			stateDefinitions.put(stateDefinition.getUUID(), stateDefinition);
		}
	}
	
	protected void addActionDefinition(ActionDefinition actionDefinition) {
		if (actionDefinitions == null) {
			throw new IllegalStateException("Must call super.init() first in init().");
		} else {
			actionDefinitions.put(actionDefinition.getUUID(), actionDefinition);
		}
	}

}
