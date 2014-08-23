package com.oakcity.nicknack.basicproviders.clock;

import java.util.UUID;

import com.oakcity.nicknack.core.events.BasicAttributeDefinition;
import com.oakcity.nicknack.core.units.StringUnit;

public class DayOfWeekAttributeDefinition extends BasicAttributeDefinition{
	
	public static final DayOfWeekAttributeDefinition INSTANCE = new DayOfWeekAttributeDefinition();

	public DayOfWeekAttributeDefinition() {
		super(UUID.fromString("47a08eac-9e3a-4c5a-b354-c3354e8f2a67"), "Day of Week", StringUnit.INSTANCE, true);
	}

}
