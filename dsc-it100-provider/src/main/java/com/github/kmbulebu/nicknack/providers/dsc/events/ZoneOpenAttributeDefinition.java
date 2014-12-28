package com.github.kmbulebu.nicknack.providers.dsc.events;

import java.util.UUID;

import com.github.kmbulebu.nicknack.core.attributes.BasicAttributeDefinition;
import com.github.kmbulebu.nicknack.core.units.BooleanUnit;

public class ZoneOpenAttributeDefinition extends BasicAttributeDefinition {
	
	public static final ZoneOpenAttributeDefinition INSTANCE = new ZoneOpenAttributeDefinition();

	public ZoneOpenAttributeDefinition() {
		super(UUID.fromString("5f86e1ae-3767-4f39-993c-7bc2366fd2da"), "Is Open", BooleanUnit.INSTANCE, false);
	}

}
