package com.github.kmbulebu.nicknack.basicproviders.clock;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.commons.configuration.Configuration;

import com.github.kmbulebu.nicknack.core.actions.Action;
import com.github.kmbulebu.nicknack.core.actions.ActionDefinition;
import com.github.kmbulebu.nicknack.core.actions.ActionFailureException;
import com.github.kmbulebu.nicknack.core.actions.ActionParameterException;
import com.github.kmbulebu.nicknack.core.events.EventDefinition;
import com.github.kmbulebu.nicknack.core.events.impl.BasicTimestampedEvent;
import com.github.kmbulebu.nicknack.core.providers.OnEventListener;
import com.github.kmbulebu.nicknack.core.providers.Provider;
import com.github.kmbulebu.nicknack.core.states.StateDefinition;

/**
 * Provides real time clock capabilities to Nick Nack.
 * @author kmbulebu
 *
 */
public class ClockProvider implements Provider, Runnable {
	
	public static final UUID PROVIDER_UUID = UUID.fromString("58f948a0-f10c-11e3-8031-d31507193ec8");
	
	private final List<EventDefinition> eventDefinitions;
	private final List<ActionDefinition> actionDefinitions;
	private final ScheduledExecutorService executorService;
	
	private OnEventListener onEventListener;
	
	public ClockProvider() {
		eventDefinitions = new ArrayList<EventDefinition>(1);
		eventDefinitions.add(ClockTickEventDefinition.INSTANCE);
		
		actionDefinitions = new ArrayList<ActionDefinition>(0);
		
		executorService = Executors.newScheduledThreadPool(2);
	}
	
	@Override
	public UUID getUuid() {
		return PROVIDER_UUID;
	}
	@Override
	public String getName() {
		return "Clock";
	}
	@Override
	public String getAuthor() {
		return "Nick Nack";
	}
	@Override
	public int getVersion() {
		return 1;
	}
	
	@Override
	public List<EventDefinition> getEventDefinitions() {
		return Collections.unmodifiableList(eventDefinitions);
	}
	
	@Override
	public Collection<StateDefinition> getStateDefinitions() {
		return Collections.emptyList();
	}
	
	@Override
	public List<ActionDefinition> getActionDefinitions() {
		return Collections.unmodifiableList(actionDefinitions);
	}
	
	@Override
	public void init(Configuration configuration, OnEventListener onEventListener) throws Exception {
		final long nextSecond = ((System.currentTimeMillis() + 2000)/1000)*1000;
		final long initialDelay = nextSecond - System.currentTimeMillis();
		executorService.scheduleAtFixedRate(this, initialDelay, 1000, TimeUnit.MILLISECONDS);
		this.onEventListener = onEventListener;
	}

	@Override
	public void run() {
		// Fire off an Event.
		if (onEventListener != null) {
			onEventListener.onEvent(new BasicTimestampedEvent(ClockTickEventDefinition.INSTANCE));
		}
	}	

	@Override
	public Map<String, String> getAttributeDefinitionValues(UUID eventDefinitionUuid, UUID attributeDefinitionUuid) {
		return null;
	}

	@Override
	public void run(Action action) throws ActionFailureException, ActionParameterException {
		// Do nothing
		return;
	}


}
