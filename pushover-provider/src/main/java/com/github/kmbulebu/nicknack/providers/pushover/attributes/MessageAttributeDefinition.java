package com.github.kmbulebu.nicknack.providers.pushover.attributes;

import java.util.UUID;

import com.github.kmbulebu.nicknack.core.attributes.BasicAttributeDefinition;
import com.github.kmbulebu.nicknack.core.units.StringUnit;

public class MessageAttributeDefinition extends BasicAttributeDefinition {

	public static final UUID DEF_UUID = UUID.fromString("1da80be0-3dbc-4f4d-ae16-37007a4d651e");
	
	public static final MessageAttributeDefinition INSTANCE = new MessageAttributeDefinition();

	public MessageAttributeDefinition() {
		super(DEF_UUID, "Message", StringUnit.INSTANCE, true);
	}

}
