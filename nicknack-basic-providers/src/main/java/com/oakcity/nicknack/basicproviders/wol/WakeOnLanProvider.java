package com.oakcity.nicknack.basicproviders.wol;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import com.oakcity.nicknack.core.Unit;
import com.oakcity.nicknack.core.actions.Action.ActionDefinition;
import com.oakcity.nicknack.core.events.Event.EventDefinition;
import com.oakcity.nicknack.core.providers.OnEventListener;
import com.oakcity.nicknack.core.providers.Provider;

/**
 * Provides real time clock capabilities to Nick Nack.
 * @author kmbulebu
 *
 */
public class WakeOnLanProvider implements Provider {
	
	public static final UUID PROVIDER_UUID = UUID.fromString("16f5774b-8104-4a29-85d7-e6d02d6353d2");
	
	private final List<EventDefinition> eventDefinitions;
	private final List<ActionDefinition> actionDefinitions;
	
	public WakeOnLanProvider() {
		eventDefinitions = new ArrayList<EventDefinition>(0);
		
		actionDefinitions = new ArrayList<ActionDefinition>(1);
		actionDefinitions.add(WakeOnLanActionDefinition.INSTANCE);

	}
	
	@Override
	public UUID getUuid() {
		return PROVIDER_UUID;
	}
	@Override
	public String getName() {
		return "WakeOnLan Provider";
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
	public List<Unit<?>> getUnits() {
		// TODO Auto-generated method stub
		return null;
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
	public void init(OnEventListener onEventListener) throws Exception {
	}

}
