package com.github.kmbulebu.nicknack.providers.xbmc.actions.parameters;

import java.util.UUID;

import com.github.kmbulebu.nicknack.core.attributes.BasicAttributeDefinition;
import com.github.kmbulebu.nicknack.core.units.StringUnit;

public class MessageAttributeDefinition extends BasicAttributeDefinition {
	
	public static final UUID DEF_UUID = UUID.fromString("b505ad85-1261-4d29-999b-4120dfb451ca");
	
	public static final MessageAttributeDefinition INSTANCE = new MessageAttributeDefinition();

	public MessageAttributeDefinition() {
		super(DEF_UUID, "Message", StringUnit.INSTANCE, true);
	}

}
