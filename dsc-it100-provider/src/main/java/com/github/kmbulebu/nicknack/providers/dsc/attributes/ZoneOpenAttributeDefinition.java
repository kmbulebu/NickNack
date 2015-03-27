package com.github.kmbulebu.nicknack.providers.dsc.attributes;

import java.util.UUID;

import com.github.kmbulebu.nicknack.core.attributes.BasicAttributeDefinition;
import com.github.kmbulebu.nicknack.core.valuetypes.builder.ValueTypeBuilder;
import com.github.kmbulebu.nicknack.core.valuetypes.impl.booleans.BooleanValueChoices;
import com.github.kmbulebu.nicknack.core.valuetypes.impl.booleans.YesNo;

public class ZoneOpenAttributeDefinition extends BasicAttributeDefinition<YesNo, Boolean> {
	
	public static final ZoneOpenAttributeDefinition INSTANCE = new ZoneOpenAttributeDefinition();

	public ZoneOpenAttributeDefinition() {
		super(UUID.fromString("5f86e1ae-3767-4f39-993c-7bc2366fd2da"), "Is Open", ValueTypeBuilder.yesNo().build(),
				new BooleanValueChoices(), 
				true,
				false);
	}

}
