package com.oakcity.nicknack.basicproviders.clock;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.commons.configuration.Configuration;

import com.oakcity.nicknack.core.actions.Action;
import com.oakcity.nicknack.core.actions.ActionDefinition;
import com.oakcity.nicknack.core.actions.ActionFailureException;
import com.oakcity.nicknack.core.actions.ActionParameterException;
import com.oakcity.nicknack.core.events.Event;
import com.oakcity.nicknack.core.events.EventDefinition;
import com.oakcity.nicknack.core.providers.OnEventListener;
import com.oakcity.nicknack.core.providers.Provider;

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
			onEventListener.onEvent(newEvent());
		}
	}	
	
	protected static final Event newEvent() {
		
		final Map<UUID, String> attributes = new HashMap<UUID, String>();
		final String currentDateTime = new SimpleDateFormat().format(new Date());
		attributes.put(ClockTickEventDefinition.DATETIME_ATTRIBUTE_DEF.getUUID(), currentDateTime);
		
		final Event event = new Event() {

			@Override
			public Map<UUID, String> getAttributes() {
				return Collections.unmodifiableMap(attributes);
			}

			@Override
			public EventDefinition getEventDefinition() {
				return ClockTickEventDefinition.INSTANCE;
			}
			
		};
		return event;
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
