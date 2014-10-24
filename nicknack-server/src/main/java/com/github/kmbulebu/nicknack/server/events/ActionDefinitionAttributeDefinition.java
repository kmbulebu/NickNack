package com.github.kmbulebu.nicknack.server.events;

import java.util.UUID;

import com.github.kmbulebu.nicknack.core.events.BasicAttributeDefinition;
import com.github.kmbulebu.nicknack.core.units.StringUnit;

public class ActionDefinitionAttributeDefinition extends BasicAttributeDefinition {
	
	public static final ActionDefinitionAttributeDefinition INSTANCE = new ActionDefinitionAttributeDefinition();

	public ActionDefinitionAttributeDefinition() {
		super(UUID.fromString("d8d992de-d876-475c-8fcd-d88e6d71146a"), "Action Definition UUID", StringUnit.INSTANCE, false);
	}
	
	

}
