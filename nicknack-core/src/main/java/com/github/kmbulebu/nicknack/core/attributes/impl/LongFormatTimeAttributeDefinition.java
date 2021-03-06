package com.github.kmbulebu.nicknack.core.attributes.impl;

import java.util.UUID;

import com.github.kmbulebu.nicknack.core.attributes.BasicAttributeDefinition;
import com.github.kmbulebu.nicknack.core.units.StringUnit;

public class LongFormatTimeAttributeDefinition extends BasicAttributeDefinition{
	
	public static final LongFormatTimeAttributeDefinition INSTANCE = new LongFormatTimeAttributeDefinition();

	public LongFormatTimeAttributeDefinition() {
		super(UUID.fromString("967e502f-3234-42e8-a285-f86f8945c32f"), "Time", StringUnit.INSTANCE, true);
	}

}
