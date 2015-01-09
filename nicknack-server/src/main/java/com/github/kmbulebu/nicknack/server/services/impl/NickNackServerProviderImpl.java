package com.github.kmbulebu.nicknack.server.services.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.kmbulebu.nicknack.core.actions.Action;
import com.github.kmbulebu.nicknack.core.actions.ActionDefinition;
import com.github.kmbulebu.nicknack.core.actions.ActionFailureException;
import com.github.kmbulebu.nicknack.core.actions.ActionParameterException;
import com.github.kmbulebu.nicknack.core.events.EventDefinition;
import com.github.kmbulebu.nicknack.core.events.impl.BasicTimestampedEvent;
import com.github.kmbulebu.nicknack.core.providers.OnEventListener;
import com.github.kmbulebu.nicknack.core.providers.Provider;
import com.github.kmbulebu.nicknack.core.providers.ProviderConfiguration;
import com.github.kmbulebu.nicknack.core.providers.ProviderService;
import com.github.kmbulebu.nicknack.core.providers.settings.ProviderSettingDefinition;
import com.github.kmbulebu.nicknack.core.states.State;
import com.github.kmbulebu.nicknack.core.states.StateDefinition;
import com.github.kmbulebu.nicknack.server.Application;
import com.github.kmbulebu.nicknack.server.actions.DummyActionDefinition;
import com.github.kmbulebu.nicknack.server.events.ActionCompletedEventDefinition;
import com.github.kmbulebu.nicknack.server.events.ActionDefinitionAttributeDefinition;
import com.github.kmbulebu.nicknack.server.events.ActionErrorMessageAttributeDefinition;
import com.github.kmbulebu.nicknack.server.events.ActionFailedEventDefinition;
import com.github.kmbulebu.nicknack.server.events.ActionNameAttributeDefinition;
import com.github.kmbulebu.nicknack.server.services.NickNackServerProvider;

@Service
public class NickNackServerProviderImpl implements Provider, NickNackServerProvider {
	
	public static final UUID PROVIDER_UUID = UUID.fromString("47bb24a8-b15d-4c4d-9218-1e9cba322d74");
	
	private List<EventDefinition> eventDefinitions;
	private List<ActionDefinition> actionDefinitions;
	private List<StateDefinition> stateDefinitions;
	
	private OnEventListener onEventListener;
	
	private static final Logger LOG = LogManager.getLogger(Application.APP_LOGGER_NAME);
	
	@Autowired
	private ProviderService providerService;
	
	@PostConstruct
	public void init() {
		if (LOG.isTraceEnabled()) {
			LOG.entry();
		}
		
		// Manually register our NickNack Server Provider
		// TOOD Do something useful with exceptions.
		providerService.addProvider(this);
		
		if (LOG.isTraceEnabled()) {
			LOG.exit();
		}
	}

	@Override
	public UUID getUuid() {
		return PROVIDER_UUID;
	}

	@Override
	public String getName() {
		return "NickNack Server";
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
		return Collections.unmodifiableCollection(eventDefinitions);
	}

	@Override
	public Collection<ActionDefinition> getActionDefinitions() {
		return Collections.unmodifiableCollection(actionDefinitions);
	}

	@Override
	public Map<String, String> getAttributeDefinitionValues(UUID attributeDefinitionUuid) {
		return null;
	}

	@Override
	public void run(Action action) throws ActionFailureException, ActionParameterException {
	}

	@Override
	public void init(ProviderConfiguration configuration, OnEventListener onEventListener) throws Exception {
		eventDefinitions = new ArrayList<>(2);
		eventDefinitions.add(ActionCompletedEventDefinition.INSTANCE);
		eventDefinitions.add(ActionFailedEventDefinition.INSTANCE);
		stateDefinitions = Collections.emptyList();
		actionDefinitions = new ArrayList<>(1);
		actionDefinitions.add(DummyActionDefinition.INSTANCE);
		this.onEventListener = onEventListener;
	}
	
	@Override
	public void shutdown() throws Exception {
		onEventListener = null;
		eventDefinitions = null;
		stateDefinitions = null;
		actionDefinitions = null;
	}
	
	@Override
	public List<? extends ProviderSettingDefinition<?>> getSettingDefinitions() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void fireActionCompletedEvent(String actionDefUuid, String actionDefName) {
		if (onEventListener != null) {
			final BasicTimestampedEvent event = new BasicTimestampedEvent(ActionCompletedEventDefinition.INSTANCE);
			event.setAttribute(ActionDefinitionAttributeDefinition.INSTANCE, actionDefUuid);
			event.setAttribute(ActionNameAttributeDefinition.INSTANCE, actionDefName);
			onEventListener.onEvent(event);
		}
	}
	
	@Override
	public void fireActionFailedEvent(String actionDefUuid, String actionDefName, String errorMessage) {
		if (onEventListener != null) {
			final BasicTimestampedEvent event = new BasicTimestampedEvent(ActionFailedEventDefinition.INSTANCE);
			event.setAttribute(ActionDefinitionAttributeDefinition.INSTANCE, actionDefUuid);
			event.setAttribute(ActionNameAttributeDefinition.INSTANCE, actionDefName);
			event.setAttribute(ActionErrorMessageAttributeDefinition.INSTANCE, errorMessage);
			onEventListener.onEvent(event);
		}
	}

	@Override
	public Collection<StateDefinition> getStateDefinitions() {
		return stateDefinitions;
	}

	@Override
	public List<State> getStates(UUID stateDefinitionUuid) {
		return Collections.emptyList();
	}

	
	

}
