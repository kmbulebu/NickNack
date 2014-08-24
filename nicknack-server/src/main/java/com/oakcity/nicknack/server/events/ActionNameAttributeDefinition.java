package com.oakcity.nicknack.server.events;

import java.util.UUID;

import com.oakcity.nicknack.core.events.BasicAttributeDefinition;
import com.oakcity.nicknack.core.units.StringUnit;

public class ActionNameAttributeDefinition extends BasicAttributeDefinition {
	
	public static final ActionNameAttributeDefinition INSTANCE = new ActionNameAttributeDefinition();

	public ActionNameAttributeDefinition() {
		super(UUID.fromString("eb57be97-d675-432c-b905-399fe9e4146f"), "Action Name", StringUnit.INSTANCE, false);
	}
	
	

}
