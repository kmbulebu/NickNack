package com.github.kmbulebu.nicknack.core.attributes.impl;

import java.util.UUID;

import com.github.kmbulebu.nicknack.core.attributes.BasicAttributeDefinition;
import com.github.kmbulebu.nicknack.core.valuetypes.builder.ValueTypeBuilder;
import com.github.kmbulebu.nicknack.core.valuetypes.impl.wholenumber.WholeNumber;
import com.github.kmbulebu.nicknack.core.valuetypes.impl.wholenumber.WholeNumberRangeChoices;

public class HourOfDayAttributeDefinition extends BasicAttributeDefinition<WholeNumber, Integer> {
	
	public static final HourOfDayAttributeDefinition INSTANCE = new HourOfDayAttributeDefinition();

	public HourOfDayAttributeDefinition() {
		super(UUID.fromString("b00abf0d-7bda-4c44-a7a1-55fe15ccc6b6"), 
				"Hour of Day",
				ValueTypeBuilder.wholeNumber().min(0).max(23).build(),
				new WholeNumberRangeChoices(0, 23), 
				true,
				false);
	}

}
