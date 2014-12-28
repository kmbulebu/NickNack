package com.github.kmbulebu.nicknack.providers.dsc.events;

import java.util.UUID;

import com.github.kmbulebu.nicknack.core.attributes.BasicAttributeDefinition;
import com.github.kmbulebu.nicknack.core.units.StringUnit;

public class ZoneLabelAttributeDefinition extends BasicAttributeDefinition {
	
	public static final ZoneLabelAttributeDefinition INSTANCE = new ZoneLabelAttributeDefinition();

	public ZoneLabelAttributeDefinition() {
		super(UUID.fromString("faa115a0-8e57-4fad-9f94-5c5c8f5d860b"), "Zone Label", StringUnit.INSTANCE, true);
	}

}
