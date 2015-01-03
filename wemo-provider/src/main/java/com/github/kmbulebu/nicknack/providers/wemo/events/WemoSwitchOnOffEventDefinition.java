package com.github.kmbulebu.nicknack.providers.wemo.events;

import java.util.UUID;

import com.github.kmbulebu.nicknack.core.events.BasicEventDefinition;
import com.github.kmbulebu.nicknack.providers.wemo.attributes.FriendlyNameAttributeDefinition;
import com.github.kmbulebu.nicknack.providers.wemo.attributes.OnAttributeDefinition;

public class WemoSwitchOnOffEventDefinition extends BasicEventDefinition {
	
	public final static WemoSwitchOnOffEventDefinition INSTANCE = new WemoSwitchOnOffEventDefinition();

	public WemoSwitchOnOffEventDefinition() {
		super(UUID.fromString("52d02af5-ffff-4ba5-a221-a25ee2560c16"), "Turned On or Off", 
				FriendlyNameAttributeDefinition.INSTANCE,
				OnAttributeDefinition.INSTANCE);
	}

}
