package com.github.kmbulebu.nicknack.providers.xbmc.attributes;

import java.util.UUID;

import com.github.kmbulebu.nicknack.core.attributes.BasicAttributeDefinition;
import com.github.kmbulebu.nicknack.core.valuetypes.TextType;

public class TitleAttributeDefinition extends BasicAttributeDefinition<TextType> {
	
	public static final UUID DEF_UUID = UUID.fromString("a43e0ab3-3bb4-4a5d-9b13-cdd9f8d20279");
	
	public static final TitleAttributeDefinition INSTANCE = new TitleAttributeDefinition();

	public TitleAttributeDefinition() {
		super(DEF_UUID, "Title", new TextType(), true);
	}

}
