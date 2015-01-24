package com.github.kmbulebu.nicknack.server.events;

import java.util.UUID;

import com.github.kmbulebu.nicknack.core.attributes.BasicAttributeDefinition;
import com.github.kmbulebu.nicknack.core.valuetypes.TextType;

public class ActionErrorMessageAttributeDefinition extends BasicAttributeDefinition<TextType> {
	
	public static final ActionErrorMessageAttributeDefinition INSTANCE = new ActionErrorMessageAttributeDefinition();

	public ActionErrorMessageAttributeDefinition() {
		super(UUID.fromString("831f533b-5058-4adc-9dce-3503ae884bd4"), "Error Message", new TextType(), false);
	}
	
	

}
