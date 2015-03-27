package com.github.kmbulebu.nicknack.providers.dsc.attributes;

import java.util.UUID;

import com.github.kmbulebu.nicknack.core.attributes.BasicAttributeDefinition;
import com.github.kmbulebu.nicknack.core.valuetypes.builder.ValueTypeBuilder;
import com.github.kmbulebu.nicknack.core.valuetypes.impl.wholenumber.WholeNumber;
import com.github.kmbulebu.nicknack.core.valuetypes.impl.wholenumber.WholeNumberRangeChoices;

public class PgmNumberAttributeDefinition extends BasicAttributeDefinition<WholeNumber, Integer> {
	
	public static final UUID DEF_UUID = UUID.fromString("b60f1f17-4024-40b3-921f-e00ce0790565");
	
	public static final PgmNumberAttributeDefinition INSTANCE = new PgmNumberAttributeDefinition();
	
	public PgmNumberAttributeDefinition() {
		super(DEF_UUID, "Pgm Output", ValueTypeBuilder.wholeNumber().min(1).max(4).build(),
				new WholeNumberRangeChoices(1, 4), 
				true,
				false);
	}


}
