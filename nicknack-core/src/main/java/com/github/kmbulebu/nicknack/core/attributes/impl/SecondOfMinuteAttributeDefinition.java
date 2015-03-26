package com.github.kmbulebu.nicknack.core.attributes.impl;

import java.util.UUID;

import com.github.kmbulebu.nicknack.core.attributes.BasicAttributeDefinition;
import com.github.kmbulebu.nicknack.core.valuetypes.builder.ValueTypeBuilder;
import com.github.kmbulebu.nicknack.core.valuetypes.impl.wholenumber.WholeNumber;
import com.github.kmbulebu.nicknack.core.valuetypes.impl.wholenumber.WholeNumberRangeChoices;

public class SecondOfMinuteAttributeDefinition extends BasicAttributeDefinition<WholeNumber, Integer>{
	
	public static final SecondOfMinuteAttributeDefinition INSTANCE = new SecondOfMinuteAttributeDefinition();

	public SecondOfMinuteAttributeDefinition() {
		super(UUID.fromString("c59e38a0-ecaa-4fe1-929c-b1641459c23a"), 
				"Second of Minute",
				ValueTypeBuilder.wholeNumber().min(0).max(59).build(),
				new WholeNumberRangeChoices(0, 59), 
				true,
				false);
	}
}
