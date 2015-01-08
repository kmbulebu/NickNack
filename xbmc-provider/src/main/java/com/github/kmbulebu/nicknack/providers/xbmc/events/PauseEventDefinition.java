package com.github.kmbulebu.nicknack.providers.xbmc.events;

import java.util.UUID;

import com.github.kmbulebu.nicknack.providers.xbmc.attributes.PlayerItemTitleAttributeDefinition;
import com.github.kmbulebu.nicknack.providers.xbmc.attributes.PlayerItemTypeAttributeDefinition;

public class PauseEventDefinition extends AbstractXbmcEventDefinition {
	
	public static final PauseEventDefinition INSTANCE = new PauseEventDefinition();

	public PauseEventDefinition() {
		super(UUID.fromString("798bf923-497e-4105-8940-69b4ad6afd5a"), "Player Paused", 
				PlayerItemTitleAttributeDefinition.INSTANCE,
				PlayerItemTypeAttributeDefinition.INSTANCE);
	}

}
