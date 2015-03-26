package com.github.kmbulebu.nicknack.core.attributes.impl;

import java.util.UUID;

import com.github.kmbulebu.nicknack.core.attributes.BasicAttributeDefinition;
import com.github.kmbulebu.nicknack.core.valuetypes.builder.ValueTypeBuilder;
import com.github.kmbulebu.nicknack.core.valuetypes.impl.wholenumber.WholeNumber;
import com.github.kmbulebu.nicknack.core.valuetypes.impl.wholenumber.WholeNumberRangeChoices;

public class MonthOfYearNumericalAttributeDefinition extends BasicAttributeDefinition<WholeNumber, Integer>{
	
	public static final MonthOfYearNumericalAttributeDefinition INSTANCE = new MonthOfYearNumericalAttributeDefinition();

	public MonthOfYearNumericalAttributeDefinition() {
		super(UUID.fromString("f9f4ce53-03a6-4d47-81d1-702663877a94"), 
				"Month of Year (Numerical)", 
				ValueTypeBuilder.wholeNumber().min(1).max(12).build(),
				new WholeNumberRangeChoices(1, 12), 
				true,
				false);
	}

}
