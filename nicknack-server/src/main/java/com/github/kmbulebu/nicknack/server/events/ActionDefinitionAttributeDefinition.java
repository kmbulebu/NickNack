package com.github.kmbulebu.nicknack.server.events;

import java.util.UUID;

import com.github.kmbulebu.nicknack.core.attributes.BasicAttributeDefinition;
import com.github.kmbulebu.nicknack.core.valuetypes.TextType;

public class ActionDefinitionAttributeDefinition extends BasicAttributeDefinition<TextType> {
	
	public static final ActionDefinitionAttributeDefinition INSTANCE = new ActionDefinitionAttributeDefinition();

	public ActionDefinitionAttributeDefinition() {
		super(UUID.fromString("d8d992de-d876-475c-8fcd-d88e6d71146a"), "Action Definition UUID", new TextType(), false);
	}
	
	

}
