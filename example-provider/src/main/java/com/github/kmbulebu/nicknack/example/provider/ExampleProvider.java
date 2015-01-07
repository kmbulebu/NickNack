package com.github.kmbulebu.nicknack.example.provider;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import org.apache.commons.configuration.Configuration;

import com.github.kmbulebu.nicknack.core.actions.Action;
import com.github.kmbulebu.nicknack.core.actions.ActionDefinition;
import com.github.kmbulebu.nicknack.core.events.EventDefinition;
import com.github.kmbulebu.nicknack.core.providers.OnEventListener;
import com.github.kmbulebu.nicknack.core.providers.Provider;
import com.github.kmbulebu.nicknack.core.states.State;
import com.github.kmbulebu.nicknack.core.states.StateDefinition;

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


	@Override
	public Map<String, String> getAttributeDefinitionValues(UUID attributeDefinitionUuid) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Collection<StateDefinition> getStateDefinitions() {
		return Collections.emptyList();
	}
	
	@Override
	public List<State> getStates(UUID stateDefinitionUuid) {
		return Collections.emptyList();
	}
	
	@Override
	public void run(Action action) {
		String macAddress = action.getAttributes().get(LightBulbActionDefinition.MAC_ADDRESS_PARAMETER_DEFINITION.getUUID());
		
		String switchStr = action.getAttributes().get(LightBulbActionDefinition.SWITCH_PARAMETER_DEFINITION.getUUID()); 
		
		if (switchStr == null) {
			// TODO Use our own exception
			throw new IllegalArgumentException(LightBulbActionDefinition.SWITCH_PARAMETER_DEFINITION.getName() + " is required.");
		}
		
		if (macAddress == null) {
			// TODO Use our own exception
			throw new IllegalArgumentException(LightBulbActionDefinition.MAC_ADDRESS_PARAMETER_DEFINITION.getName() + " is required.");
		}
		
		// Our dummy action
		System.out.println("Switch changing to " + switchStr);
		
	}
	
	

}
