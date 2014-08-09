package com.oakcity.nicknack.providers.dsc.events;

import java.util.UUID;

import com.oakcity.nicknack.core.events.BasicEventDefinition;

public class ZoneOpenCloseEventDefinition extends BasicEventDefinition {
	
	public static final ZoneOpenCloseEventDefinition INSTANCE = new ZoneOpenCloseEventDefinition();

	private ZoneOpenCloseEventDefinition() {
		super(UUID.fromString("dea8dade-dbec-4eef-b684-8ef28851a044"), 
				"Zone Opened or Restored", 
				ZoneLabelAttributeDefinition.INSTANCE,
				ZoneNumberAttributeDefinition.INSTANCE,
				ZoneOpenAttributeDefinition.INSTANCE);
	}

}