package com.oakcity.nicknack.basicproviders.clock;

import java.util.UUID;

import com.oakcity.nicknack.core.events.BasicAttributeDefinition;
import com.oakcity.nicknack.core.units.StringUnit;

public class YearAttributeDefinition extends BasicAttributeDefinition{
	
	public static final YearAttributeDefinition INSTANCE = new YearAttributeDefinition();

	public YearAttributeDefinition() {
		super(UUID.fromString("9f2cdc64-8cb6-4289-a865-b6c254292913"), "Year (4 digit)", StringUnit.INSTANCE, true);
	}

}
