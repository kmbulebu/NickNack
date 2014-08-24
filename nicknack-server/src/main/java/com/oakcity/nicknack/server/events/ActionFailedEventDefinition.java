package com.oakcity.nicknack.server.events;

import java.util.UUID;

import com.oakcity.nicknack.core.events.impl.BasicTimestampedEventDefinition;

public class ActionFailedEventDefinition extends BasicTimestampedEventDefinition {
	
	private static final UUID DEF_UUID = UUID.fromString("f7e0b34d-d55f-4e1a-82d0-d3c6d57dfc92");
	private static final String NAME = "NickNack Action Failed";
	public static final ActionFailedEventDefinition INSTANCE = new ActionFailedEventDefinition();

	public ActionFailedEventDefinition() {
		super(DEF_UUID, NAME,
				ActionDefinitionAttributeDefinition.INSTANCE,
				ActionNameAttributeDefinition.INSTANCE);
	}
	
}
