package com.github.kmbulebu.nicknack.core.events.impl;

import java.util.UUID;

import com.github.kmbulebu.nicknack.core.attributes.BasicAttributeDefinition;
import com.github.kmbulebu.nicknack.core.units.StringUnit;

public class LongFormatDateAttributeDefinition extends BasicAttributeDefinition{
	
	public static final LongFormatDateAttributeDefinition INSTANCE = new LongFormatDateAttributeDefinition();

	public LongFormatDateAttributeDefinition() {
		super(UUID.fromString("dd05c183-fdf2-4e09-af9e-f39e81c7a292"), "Date", StringUnit.INSTANCE, true);
	}

}
