package com.github.kmbulebu.nicknack.providers.wemo.states;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.github.kmbulebu.nicknack.core.states.State;
import com.github.kmbulebu.nicknack.core.states.StateDefinition;
import com.github.kmbulebu.nicknack.providers.wemo.attributes.FriendlyNameAttributeDefinition;
import com.github.kmbulebu.nicknack.providers.wemo.attributes.OnAttributeDefinition;
import com.github.kmbulebu.nicknack.providers.wemo.internal.WemoDevice;
import com.github.kmbulebu.nicknack.providers.wemo.internal.WemoException;

public class WemoSwitchState implements State {
	
	private final Map<UUID, String> attributes = new HashMap<UUID, String>();

	@Override
	public Map<UUID, String> getAttributes() {
		return Collections.unmodifiableMap(attributes);
	}

	@Override
	public StateDefinition getStateDefinition() {
		return WemoSwitchStateDefinition.INSTANCE;
	}
	
	public void setFriendlyName(String friendlyName) {
		attributes.put(FriendlyNameAttributeDefinition.INSTANCE.getUUID(), friendlyName);
	}
	
	public void setIsOn(boolean isOn) {
		attributes.put(OnAttributeDefinition.INSTANCE.getUUID(), Boolean.toString(isOn));
	}
	
	public static WemoSwitchState build(WemoDevice device) throws WemoException {
		final WemoSwitchState state = new WemoSwitchState();
		
		state.setFriendlyName(device.getFriendlyName());
		state.setIsOn(device.isOn());
		
		return state;
	}

}
