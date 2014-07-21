package com.oakcity.nicknack.providers.xbmc.events;

import java.util.UUID;

public class PlayEventDefinition extends BaseEventDefinition {
	
	public static final PlayEventDefinition INSTANCE = new PlayEventDefinition();

	public PlayEventDefinition() {
		super(UUID.fromString("d0a95beb-6306-449f-b873-c427411d92d4"), "Player Started/Resumed", 
				PlayerItemTitleAttributeDefinition.INSTANCE,
				PlayerItemTypeAttributeDefinition.INSTANCE);
	}

}
