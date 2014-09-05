package com.oakcity.nicknack.server;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.configuration.Configuration;

import com.oakcity.nicknack.core.actions.Action;
import com.oakcity.nicknack.core.actions.ActionDefinition;
import com.oakcity.nicknack.core.actions.ActionFailureException;
import com.oakcity.nicknack.core.actions.ActionParameterException;
import com.oakcity.nicknack.core.events.EventDefinition;
import com.oakcity.nicknack.core.events.impl.BasicTimestampedEvent;
import com.oakcity.nicknack.core.providers.OnEventListener;
import com.oakcity.nicknack.core.providers.Provider;
import com.oakcity.nicknack.server.events.ActionCompletedEventDefinition;
import com.oakcity.nicknack.server.events.ActionDefinitionAttributeDefinition;
import com.oakcity.nicknack.server.events.ActionErrorMessageAttributeDefinition;
import com.oakcity.nicknack.server.events.ActionFailedEventDefinition;
import com.oakcity.nicknack.server.events.ActionNameAttributeDefinition;

public class NickNackServerProvider implements Provider {
	
	public static final UUID PROVIDER_UUID = UUID.fromString("47bb24a8-b15d-4c4d-9218-1e9cba322d74");
	
	private final List<EventDefinition> eventDefinitions;
	
	private OnEventListener onEventListener;
	
	public NickNackServerProvider() {
		eventDefinitions = new ArrayList<>(2);
		eventDefinitions.add(ActionCompletedEventDefinition.INSTANCE);
		eventDefinitions.add(ActionFailedEventDefinition.INSTANCE);
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
		return Collections.emptyList();
	}

	@Override
	public Map<String, String> getAttributeDefinitionValues(UUID eventDefinitionUuid, UUID attributeDefinitionUuid) {
		return null;
	}

	@Override
	public void run(Action action) throws ActionFailureException, ActionParameterException {
	}

	@Override
	public void init(Configuration configuration, OnEventListener onEventListener) throws Exception {
		this.onEventListener = onEventListener;
	}
	
	public void fireActionCompletedEvent(String actionDefUuid, String actionDefName) {
		if (onEventListener != null) {
			final BasicTimestampedEvent event = new BasicTimestampedEvent(ActionCompletedEventDefinition.INSTANCE);
			event.setAttribute(ActionDefinitionAttributeDefinition.INSTANCE, actionDefUuid);
			event.setAttribute(ActionNameAttributeDefinition.INSTANCE, actionDefName);
			onEventListener.onEvent(event);
		}
	}
	
	public void fireActionFailedEvent(String actionDefUuid, String actionDefName, String errorMessage) {
		if (onEventListener != null) {
			final BasicTimestampedEvent event = new BasicTimestampedEvent(ActionFailedEventDefinition.INSTANCE);
			event.setAttribute(ActionDefinitionAttributeDefinition.INSTANCE, actionDefUuid);
			event.setAttribute(ActionNameAttributeDefinition.INSTANCE, actionDefName);
			event.setAttribute(ActionErrorMessageAttributeDefinition.INSTANCE, errorMessage);
			onEventListener.onEvent(event);
		}
	}

	
	

}
