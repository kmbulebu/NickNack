package com.github.kmbulebu.nicknack.providers.xbmc.events;

import java.util.UUID;

import com.github.kmbulebu.nicknack.core.events.BasicAttributeDefinition;
import com.github.kmbulebu.nicknack.core.units.StringUnit;

public class PlayerItemTitleAttributeDefinition extends BasicAttributeDefinition{
	
	public static final PlayerItemTitleAttributeDefinition INSTANCE = new PlayerItemTitleAttributeDefinition();

	public PlayerItemTitleAttributeDefinition() {
		super(UUID.fromString("772175de-46c8-4a18-8fe1-1bb34618a3d1"), "Media Title", StringUnit.INSTANCE, true);
	}

}
