package com.github.kmbulebu.nicknack.providers.wemo.events;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.github.kmbulebu.nicknack.core.events.Event;
import com.github.kmbulebu.nicknack.core.events.EventDefinition;
import com.github.kmbulebu.nicknack.providers.wemo.attributes.FriendlyNameAttributeDefinition;
import com.github.kmbulebu.nicknack.providers.wemo.attributes.OnAttributeDefinition;

public class WemoSwitchOnOffEvent implements Event {
	
	private final Map<UUID, String> attributes = new HashMap<>();
	
	private final Date created;
	
	public WemoSwitchOnOffEvent(Date created) {
		this.created = created;
	}

	@Override
	public Map<UUID, String> getAttributes() {
		return attributes;
	}

	@Override
	public EventDefinition getEventDefinition() {
		return WemoSwitchOnOffEventDefinition.INSTANCE;
	}

	@Override
	public Date getCreated() {
		return created;
	}
	
	public void setFriendlyName(String friendlyName) {
		attributes.put(FriendlyNameAttributeDefinition.INSTANCE.getUUID(), friendlyName);
	}
	
	public void setIsOn(boolean isOn) {
		attributes.put(OnAttributeDefinition.INSTANCE.getUUID(), Boolean.toString(isOn));
	}

}
