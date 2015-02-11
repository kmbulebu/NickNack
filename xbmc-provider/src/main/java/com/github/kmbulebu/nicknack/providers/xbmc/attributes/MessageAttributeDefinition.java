package com.github.kmbulebu.nicknack.providers.xbmc.attributes;

import java.util.UUID;

import com.github.kmbulebu.nicknack.core.attributes.BasicAttributeDefinition;
import com.github.kmbulebu.nicknack.core.valuetypes.TextType;

public class MessageAttributeDefinition extends BasicAttributeDefinition<TextType> {
	
	public static final UUID DEF_UUID = UUID.fromString("b505ad85-1261-4d29-999b-4120dfb451ca");
	
	public static final MessageAttributeDefinition INSTANCE = new MessageAttributeDefinition();

	public MessageAttributeDefinition() {
		super(DEF_UUID, "Message", new TextType(), true);
	}

}
