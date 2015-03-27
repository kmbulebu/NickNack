package com.github.kmbulebu.nicknack.providers.dsc.attributes;

import java.util.UUID;

import com.github.kmbulebu.nicknack.core.attributes.BasicAttributeDefinition;
import com.github.kmbulebu.nicknack.core.valuetypes.builder.ValueTypeBuilder;
import com.github.kmbulebu.nicknack.core.valuetypes.impl.wholenumber.WholeNumber;
import com.github.kmbulebu.nicknack.core.valuetypes.impl.wholenumber.WholeNumberRangeChoices;

public class ZoneNumberAttributeDefinition extends BasicAttributeDefinition<WholeNumber, Integer> {
	
	public static final ZoneNumberAttributeDefinition INSTANCE = new ZoneNumberAttributeDefinition();

	public ZoneNumberAttributeDefinition() {
		super(UUID.fromString("2c85db6a-3702-49ec-8b40-be550724177f"), "Zone Number", 
				ValueTypeBuilder.wholeNumber().min(1).max(128).build(),
				new WholeNumberRangeChoices(1, 128), 
				true,
				false);
	}

}
