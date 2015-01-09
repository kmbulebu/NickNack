package com.github.kmbulebu.nicknack.providers.xbmc.events;

import java.util.UUID;

import com.github.kmbulebu.nicknack.providers.xbmc.attributes.PlayerItemTitleAttributeDefinition;
import com.github.kmbulebu.nicknack.providers.xbmc.attributes.PlayerItemTypeAttributeDefinition;

public class StopEventDefinition extends AbstractXbmcEventDefinition {

	public static final StopEventDefinition INSTANCE = new StopEventDefinition();
	
	public StopEventDefinition() {
		super(UUID.fromString("15261f8d-0048-4cc3-a223-a20a99a367bd"), "Player Stopped", 
				PlayerItemTitleAttributeDefinition.INSTANCE,
				PlayerItemTypeAttributeDefinition.INSTANCE);
	}

}
