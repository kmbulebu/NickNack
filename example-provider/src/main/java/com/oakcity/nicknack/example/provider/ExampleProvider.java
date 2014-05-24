package com.oakcity.nicknack.example.provider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.oakcity.nicknack.core.Unit;
import com.oakcity.nicknack.core.actions.Action.ActionDefinition;
import com.oakcity.nicknack.core.events.Event.EventDefinition;
import com.oakcity.nicknack.core.providers.Provider;

public class ExampleProvider implements Provider {
	
	private final List<EventDefinition> eventDefinitions;
	
	public ExampleProvider () {
		eventDefinitions = new ArrayList<EventDefinition>();
		
		// Switch change event.
		eventDefinitions.add(new SwitchChangeEventDefinition());
	}
	

	@Override
	public List<Unit<?>> getUnits() {
		// Does not define any new Units.
		return Collections.emptyList();
	}

	@Override
	public List<EventDefinition> getEventDefinitions() {
		return eventDefinitions;
	}

	@Override
	public List<ActionDefinition> getActionDefinitions() {
		// Does not provide any actions.
		return Collections.emptyList();
	}


	@Override
	public String getName() {
		return "Example Light Bulb and Switch Provider";
	}


	@Override
	public int getVersion() {
		return 1;
	}

}
