package com.oakcity.nicknack.server.events;

import java.util.UUID;

import com.oakcity.nicknack.core.events.BasicAttributeDefinition;
import com.oakcity.nicknack.core.units.StringUnit;

public class ActionDefinitionAttributeDefinition extends BasicAttributeDefinition {
	
	public static final ActionDefinitionAttributeDefinition INSTANCE = new ActionDefinitionAttributeDefinition();

	public ActionDefinitionAttributeDefinition() {
		super(UUID.fromString("d8d992de-d876-475c-8fcd-d88e6d71146a"), "Action Definition UUID", StringUnit.INSTANCE, false);
	}
	
	

}
