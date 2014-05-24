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
	private final List<ActionDefinition> actionDefinitions;
	
	public ExampleProvider () {
		eventDefinitions = new ArrayList<EventDefinition>();
		actionDefinitions = new ArrayList<ActionDefinition>();
		
		// Switch change event.
		eventDefinitions.add(new SwitchChangeEventDefinition());
		actionDefinitions.add(new LightBulbActionDefinition());
	}
	

	@Override
	public List<Unit<?>> getUnits() {
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

}
