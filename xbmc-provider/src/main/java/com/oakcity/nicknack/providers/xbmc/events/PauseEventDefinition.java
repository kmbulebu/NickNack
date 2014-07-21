package com.oakcity.nicknack.providers.xbmc.events;

import java.util.UUID;

public class PauseEventDefinition extends BaseEventDefinition {
	
	public static final PauseEventDefinition INSTANCE = new PauseEventDefinition();

	public PauseEventDefinition() {
		super(UUID.fromString("798bf923-497e-4105-8940-69b4ad6afd5a"), "Player Paused", 
				PlayerItemTitleAttributeDefinition.INSTANCE,
				PlayerItemTypeAttributeDefinition.INSTANCE);
	}

}
