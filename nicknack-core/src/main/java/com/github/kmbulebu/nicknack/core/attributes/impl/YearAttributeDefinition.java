package com.github.kmbulebu.nicknack.core.attributes.impl;

import java.util.Calendar;
import java.util.UUID;

import com.github.kmbulebu.nicknack.core.attributes.BasicAttributeDefinition;
import com.github.kmbulebu.nicknack.core.valuetypes.builder.ValueTypeBuilder;
import com.github.kmbulebu.nicknack.core.valuetypes.impl.wholenumber.WholeNumber;
import com.github.kmbulebu.nicknack.core.valuetypes.impl.wholenumber.WholeNumberRangeChoices;

public class YearAttributeDefinition extends BasicAttributeDefinition<WholeNumber, Integer>{
	
	public static final YearAttributeDefinition INSTANCE = new YearAttributeDefinition();
	
	private static final int MIN_YEAR = Calendar.getInstance().get(Calendar.YEAR);
	private static final int MAX_YEAR = MIN_YEAR + 10;

	public YearAttributeDefinition() {
		super(UUID.fromString("9f2cdc64-8cb6-4289-a865-b6c254292913"), 
				"Year (4 digit)", 
				ValueTypeBuilder.wholeNumber().min(MIN_YEAR).max(MAX_YEAR).build(),
				new WholeNumberRangeChoices(MIN_YEAR, MAX_YEAR), 
				true,
				false);
	}

}
