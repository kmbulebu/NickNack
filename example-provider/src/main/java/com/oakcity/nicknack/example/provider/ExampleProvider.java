package com.oakcity.nicknack.example.provider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import org.apache.commons.configuration.Configuration;

import com.oakcity.nicknack.core.actions.ActionDefinition;
import com.oakcity.nicknack.core.events.EventDefinition;
import com.oakcity.nicknack.core.providers.OnEventListener;
import com.oakcity.nicknack.core.providers.Provider;
import com.oakcity.nicknack.core.units.Unit;

public class ExampleProvider implements Provider {
	
	public static final UUID PROVIDER_UUID = UUID.fromString("766bac6a-e6d1-11e3-a880-8fb61c8a7442");
	
	private final List<EventDefinition> eventDefinitions;
	private final List<ActionDefinition> actionDefinitions;
	
	final Random random = new Random();
	
	final SwitchChangeEventFactory switchChangeEventFactory = new SwitchChangeEventFactory();
	
	public ExampleProvider () {
		eventDefinitions = new ArrayList<EventDefinition>();
		actionDefinitions = new ArrayList<ActionDefinition>();
		
		// Switch change event.
		eventDefinitions.add(SwitchChangeEventDefinition.INSTANCE);
		actionDefinitions.add(new LightBulbActionDefinition());
	}
	

	@Override
	public List<Unit> getUnits() {
		// Does not define any new Units.
		return Collections.emptyList();
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
	public String getName() {
		return "Example Light Bulb and Switch Provider";
	}


	@Override
	public int getVersion() {
		return 1;
	}


	@Override
	public String getAuthor() {
		return "NickNack";
	}
	
	@Override
	public void init(Configuration configuration, final OnEventListener onEvent) throws Exception {
		// Every 30 seconds, the light switch is randomly turned on or off.
		final Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {
				final String macAddress = "0b:41:c1:3a:89:36";
				final boolean position = random.nextBoolean();
				onEvent.onEvent(switchChangeEventFactory.newEvent(position, macAddress));
			}
			
		}, 10000, 10000);
	}


	@Override
	public UUID getUuid() {
		return PROVIDER_UUID;
	}

}
