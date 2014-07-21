package com.oakcity.nicknack.providers.xbmc.events;

import java.util.UUID;

import com.oakcity.nicknack.core.events.BasicAttributeDefinition;
import com.oakcity.nicknack.core.units.StringUnit;

public class SourceHostAttributeDefinition extends BasicAttributeDefinition {
	
	public static final SourceHostAttributeDefinition INSTANCE = new SourceHostAttributeDefinition();

	public SourceHostAttributeDefinition() {
		super(UUID.fromString("0a853b5b-4387-4258-b2f6-26fb5b6aba94"), "Source Host", StringUnit.INSTANCE, false);
	}

}
