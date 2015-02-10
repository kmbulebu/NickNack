package com.github.kmbulebu.nicknack.providers.dsc.attributes;

import java.util.UUID;

import com.github.kmbulebu.nicknack.core.attributes.BasicAttributeDefinition;
import com.github.kmbulebu.nicknack.core.valuetypes.WholeNumberType;

public class ZoneNumberAttributeDefinition extends BasicAttributeDefinition<WholeNumberType> {
	
	public static final ZoneNumberAttributeDefinition INSTANCE = new ZoneNumberAttributeDefinition();

	public ZoneNumberAttributeDefinition() {
		super(UUID.fromString("2c85db6a-3702-49ec-8b40-be550724177f"), "Zone Number", new WholeNumberType(1, 128, 1), false);
	}

}
