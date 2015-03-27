package com.github.kmbulebu.nicknack.basicproviders.clock;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.github.kmbulebu.nicknack.core.attributes.AttributeCollection;
import com.github.kmbulebu.nicknack.core.events.impl.BasicTimestampedEvent;
import com.github.kmbulebu.nicknack.core.providers.BaseProvider;
import com.github.kmbulebu.nicknack.core.providers.OnEventListener;
import com.github.kmbulebu.nicknack.core.states.State;
import com.github.kmbulebu.nicknack.core.states.StateDefinition;

/**
 * Provides real time clock capabilities to Nick Nack.
 * @author kmbulebu
 *
 */
public class ClockProvider extends BaseProvider implements Runnable {
	
	public static final UUID PROVIDER_UUID = UUID.fromString("58f948a0-f10c-11e3-8031-d31507193ec8");
	
	private ScheduledExecutorService executorService;
	
	private OnEventListener onEventListener;
	
	public ClockProvider() {
		super(PROVIDER_UUID, "Clock", "NickNack", 1);
	}
	
	@Override
	public void init(AttributeCollection settings, OnEventListener onEventListener) throws Exception {
		super.init(settings,  onEventListener);
		addEventDefinition(ClockTickEventDefinition.INSTANCE);
		addStateDefinition(ClockStateDefinition.INSTANCE);
		final long nextSecond = ((System.currentTimeMillis() + 2000)/1000)*1000;
		final long initialDelay = nextSecond - System.currentTimeMillis();
		
		executorService = Executors.newScheduledThreadPool(2);
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
	protected List<State> getStates(StateDefinition stateDefinition) {
		if (ClockStateDefinition.INSTANCE.equals(stateDefinition)) {
			return getClockStates();
		} else {
			return Collections.emptyList();
		}
	}

	private List<State> getClockStates() {
		final List<State> states = new ArrayList<>();
		states.add(new ClockState());	
		return states;
	}

	@Override
	public void shutdown() throws Exception {
		super.shutdown();
		executorService.shutdown();
		executorService = null;
		onEventListener = null;
	}

}
