package com.oakcity.nicknack.server.events;

import java.util.UUID;

import com.oakcity.nicknack.core.events.impl.BasicTimestampedEventDefinition;

public class ActionCompletedEventDefinition extends BasicTimestampedEventDefinition {
	
	private static final UUID DEF_UUID = UUID.fromString("8eed1146-96a0-4696-9a86-92a3d09deb6f");
	private static final String NAME = "NickNack Action Completed";
	public static final ActionCompletedEventDefinition INSTANCE = new ActionCompletedEventDefinition();

	public ActionCompletedEventDefinition() {
		super(DEF_UUID, NAME,
				ActionDefinitionAttributeDefinition.INSTANCE,
				ActionNameAttributeDefinition.INSTANCE);
	}
	
}
