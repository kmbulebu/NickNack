package com.github.kmbulebu.nicknack.providers.dsc.events;

import java.util.UUID;

import com.github.kmbulebu.nicknack.core.events.impl.BasicTimestampedEventDefinition;

public class ZoneOpenCloseEventDefinition extends BasicTimestampedEventDefinition {
	
	public static final ZoneOpenCloseEventDefinition INSTANCE = new ZoneOpenCloseEventDefinition();

	private ZoneOpenCloseEventDefinition() {
		super(UUID.fromString("dea8dade-dbec-4eef-b684-8ef28851a044"), 
				"Zone Opened or Restored", 
				ZoneLabelAttributeDefinition.INSTANCE,
				ZoneNumberAttributeDefinition.INSTANCE,
				ZoneOpenAttributeDefinition.INSTANCE);
	}

}
