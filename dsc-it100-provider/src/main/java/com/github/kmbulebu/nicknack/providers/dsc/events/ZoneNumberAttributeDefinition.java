package com.github.kmbulebu.nicknack.providers.dsc.events;

import java.util.UUID;

import com.github.kmbulebu.nicknack.core.events.BasicAttributeDefinition;
import com.github.kmbulebu.nicknack.core.units.IntegerUnit;

public class ZoneNumberAttributeDefinition extends BasicAttributeDefinition {
	
	public static final ZoneNumberAttributeDefinition INSTANCE = new ZoneNumberAttributeDefinition();

	public ZoneNumberAttributeDefinition() {
		super(UUID.fromString("2c85db6a-3702-49ec-8b40-be550724177f"), "Zone Number", IntegerUnit.INSTANCE, false);
	}

}
