package com.oakcity.nicknack.providers.dsc.events;

import java.util.UUID;

import com.oakcity.nicknack.core.events.BasicAttributeDefinition;
import com.oakcity.nicknack.core.units.IntegerUnit;

public class ZoneNumberAttributeDefinition extends BasicAttributeDefinition {
	
	public static final ZoneNumberAttributeDefinition INSTANCE = new ZoneNumberAttributeDefinition();

	public ZoneNumberAttributeDefinition() {
		super(UUID.fromString("2c85db6a-3702-49ec-8b40-be550724177f"), "Zone Number", IntegerUnit.INSTANCE, false);
	}

}
