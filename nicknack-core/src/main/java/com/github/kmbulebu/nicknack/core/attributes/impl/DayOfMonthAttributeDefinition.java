package com.github.kmbulebu.nicknack.core.attributes.impl;

import java.util.UUID;

import com.github.kmbulebu.nicknack.core.attributes.BasicAttributeDefinition;
import com.github.kmbulebu.nicknack.core.valuetypes.builder.ValueTypeBuilder;
import com.github.kmbulebu.nicknack.core.valuetypes.impl.wholenumber.WholeNumber;
import com.github.kmbulebu.nicknack.core.valuetypes.impl.wholenumber.WholeNumberRangeChoices;

public class DayOfMonthAttributeDefinition extends BasicAttributeDefinition<WholeNumber,Integer> {
	
	public static final DayOfMonthAttributeDefinition INSTANCE = new DayOfMonthAttributeDefinition();

	public DayOfMonthAttributeDefinition() {
		super(UUID.fromString("99dca082-5d49-40ab-bbe2-853b303f3192"), 
				"Day of Month", 
				ValueTypeBuilder.wholeNumber().min(1).max(31).build(),
				new WholeNumberRangeChoices(1, 31), 
				true,
				false);
	}

}
