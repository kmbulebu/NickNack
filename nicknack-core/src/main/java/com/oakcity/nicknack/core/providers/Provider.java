package com.oakcity.nicknack.core.providers;

import java.util.List;

import com.oakcity.nicknack.core.Unit;
import com.oakcity.nicknack.core.actions.Action.ActionDefinition;
import com.oakcity.nicknack.core.events.Event.EventDefinition;

public interface Provider {
	
	public List<Unit<?>> getUnits();
	
	//public List<AttributeDefinition> getAttributeDefinitions();
	
	public List<EventDefinition> getEventDefinitions();
	
	public List<ActionDefinition> getActionDefinitions();
	
	// TODO A method or something that returns Observables for Events (A stream of actual events)
}
