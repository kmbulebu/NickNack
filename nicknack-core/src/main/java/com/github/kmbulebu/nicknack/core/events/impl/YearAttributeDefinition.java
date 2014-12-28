package com.github.kmbulebu.nicknack.core.events.impl;

import java.util.UUID;

import com.github.kmbulebu.nicknack.core.attributes.BasicAttributeDefinition;
import com.github.kmbulebu.nicknack.core.units.StringUnit;

public class YearAttributeDefinition extends BasicAttributeDefinition{
	
	public static final YearAttributeDefinition INSTANCE = new YearAttributeDefinition();

	public YearAttributeDefinition() {
		super(UUID.fromString("9f2cdc64-8cb6-4289-a865-b6c254292913"), "Year (4 digit)", StringUnit.INSTANCE, true);
	}

}
