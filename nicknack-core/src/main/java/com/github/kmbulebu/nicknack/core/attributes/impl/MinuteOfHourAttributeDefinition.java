package com.github.kmbulebu.nicknack.core.attributes.impl;

import java.util.UUID;

import com.github.kmbulebu.nicknack.core.attributes.BasicAttributeDefinition;
import com.github.kmbulebu.nicknack.core.valuetypes.builder.ValueTypeBuilder;
import com.github.kmbulebu.nicknack.core.valuetypes.impl.wholenumber.WholeNumber;
import com.github.kmbulebu.nicknack.core.valuetypes.impl.wholenumber.WholeNumberRangeChoices;

public class MinuteOfHourAttributeDefinition extends BasicAttributeDefinition<WholeNumber, Integer>{
	
	public static final MinuteOfHourAttributeDefinition INSTANCE = new MinuteOfHourAttributeDefinition();

	public MinuteOfHourAttributeDefinition() {
		super(UUID.fromString("d07bf3e7-bda0-48ac-a6b9-f88a11495b49"), 
				"Minute of Hour", 
				ValueTypeBuilder.wholeNumber().min(0).max(59).build(),
				new WholeNumberRangeChoices(0, 59), 
				true,
				false);
	}

}
