package com.oakcity.nicknack.providers.xbmc.events;

import java.util.UUID;

import com.oakcity.nicknack.core.events.BasicEventDefinition;

public class StopEventDefinition extends BasicEventDefinition {

	public static final StopEventDefinition INSTANCE = new StopEventDefinition();
	
	public StopEventDefinition() {
		super(UUID.fromString("15261f8d-0048-4cc3-a223-a20a99a367bd"), "Player Stopped", 
				PlayerItemTitleAttributeDefinition.INSTANCE,
				PlayerItemTypeAttributeDefinition.INSTANCE);
	}

}
