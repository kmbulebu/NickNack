package com.oakcity.nicknack.providers.xbmc.events;

import java.util.UUID;

import com.oakcity.nicknack.core.events.BasicEventDefinition;

public class PauseEventDefinition extends BasicEventDefinition {
	
	public static final PauseEventDefinition INSTANCE = new PauseEventDefinition();

	public PauseEventDefinition() {
		super(UUID.fromString("798bf923-497e-4105-8940-69b4ad6afd5a"), "Player paused", 
				PlayerItemTitleAttributeDefinition.INSTANCE,
				PlayerItemTypeAttributeDefinition.INSTANCE);
	}

}
