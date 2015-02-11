package com.github.kmbulebu.nicknack.providers.xbmc.attributes;

import java.util.UUID;

import com.github.kmbulebu.nicknack.core.attributes.BasicAttributeDefinition;
import com.github.kmbulebu.nicknack.core.valuetypes.TextType;

public class PlayerItemTitleAttributeDefinition extends BasicAttributeDefinition<TextType> {
	
	public static final PlayerItemTitleAttributeDefinition INSTANCE = new PlayerItemTitleAttributeDefinition();

	public PlayerItemTitleAttributeDefinition() {
		super(UUID.fromString("772175de-46c8-4a18-8fe1-1bb34618a3d1"), "Media Title", new TextType(), true);
	}

}
