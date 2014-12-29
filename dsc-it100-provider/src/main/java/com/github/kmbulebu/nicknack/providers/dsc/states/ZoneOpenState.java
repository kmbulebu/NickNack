package com.github.kmbulebu.nicknack.providers.dsc.states;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.github.kmbulebu.nicknack.core.states.State;
import com.github.kmbulebu.nicknack.core.states.StateDefinition;
import com.github.kmbulebu.nicknack.providers.dsc.attributes.ZoneLabelAttributeDefinition;
import com.github.kmbulebu.nicknack.providers.dsc.attributes.ZoneNumberAttributeDefinition;
import com.github.kmbulebu.nicknack.providers.dsc.attributes.ZoneOpenAttributeDefinition;

public class ZoneOpenState implements State {
	
	final Map<UUID, String> attributes = new HashMap<>();

	@Override
	public Map<UUID, String> getAttributes() {
		return Collections.unmodifiableMap(attributes);
	}

	@Override
	public StateDefinition getStateDefinition() {
		return ZoneOpenStateDefinition.INSTANCE; 
	}
	
	public void setZoneNumber(int zoneNumber) {
		attributes.put(ZoneNumberAttributeDefinition.INSTANCE.getUUID(), Integer.toString(zoneNumber));
	}
	
	public void setZoneLabel(String label) {
		attributes.put(ZoneLabelAttributeDefinition.INSTANCE.getUUID(), label);
	}
	
	public void setZoneOpen(boolean isOpen) {
		attributes.put(ZoneOpenAttributeDefinition.INSTANCE.getUUID(), Boolean.toString(isOpen));
	}

}
