package com.oakcity.nicknack.example.provider;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.oakcity.nicknack.core.events.Event;
import com.oakcity.nicknack.core.events.EventDefinition;

public class SwitchChangeEventFactory {
	
	public Event newEvent(boolean position, final String macAddress) {
		final Map<UUID, String> attributes = new HashMap<UUID, String>();
		
		attributes.put(SwitchChangeEventDefinition.POSITION_ATTRIBUTE_DEF.getUUID(), Boolean.toString(position));
		attributes.put(SwitchChangeEventDefinition.MAC_ADDRESS_ATTRIBUTE_DEF.getUUID(), macAddress);
		
		return new Event() {

			@Override
			public Map<UUID, String> getAttributes() {
				return attributes;
			}

			@Override
			public EventDefinition getEventDefinition() {
				return SwitchChangeEventDefinition.INSTANCE;
			}
			
		};
	}

}