package com.oakcity.nicknack.providers.xbmc.events;

import java.util.UUID;

import com.oakcity.nicknack.core.events.BasicEventDefinition;

public class PlayEventDefinition extends BasicEventDefinition {
	
	public static final PlayEventDefinition INSTANCE = new PlayEventDefinition();

	public PlayEventDefinition() {
		super(UUID.fromString("d0a95beb-6306-449f-b873-c427411d92d4"), "Player started", 
				PlayerItemTitleAttributeDefinition.INSTANCE,
				PlayerItemTypeAttributeDefinition.INSTANCE);
	}

}
