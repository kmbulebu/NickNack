package com.github.kmbulebu.nicknack.providers.wemo.states;

import java.util.UUID;

import com.github.kmbulebu.nicknack.core.states.BasicStateDefinition;
import com.github.kmbulebu.nicknack.providers.wemo.attributes.FriendlyNameAttributeDefinition;
import com.github.kmbulebu.nicknack.providers.wemo.attributes.OnAttributeDefinition;

public class WemoSwitchStateDefinition extends BasicStateDefinition {
	
	public final static WemoSwitchStateDefinition INSTANCE = new WemoSwitchStateDefinition();

	public WemoSwitchStateDefinition() {
		super(UUID.fromString("179e7470-8938-4176-b765-ec4c392f5fc0"), "Switch", 
				FriendlyNameAttributeDefinition.INSTANCE,
				OnAttributeDefinition.INSTANCE);
	}

}
