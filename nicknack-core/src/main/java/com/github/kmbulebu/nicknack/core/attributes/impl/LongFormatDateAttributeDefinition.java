package com.github.kmbulebu.nicknack.core.attributes.impl;

import java.util.UUID;

import com.github.kmbulebu.nicknack.core.attributes.BasicAttributeDefinition;
import com.github.kmbulebu.nicknack.core.valuetypes.builder.ValueTypeBuilder;
import com.github.kmbulebu.nicknack.core.valuetypes.impl.text.Text;

public class LongFormatDateAttributeDefinition extends BasicAttributeDefinition<Text, String> {
	
	public static final LongFormatDateAttributeDefinition INSTANCE = new LongFormatDateAttributeDefinition();

	public LongFormatDateAttributeDefinition() {
		super(UUID.fromString("dd05c183-fdf2-4e09-af9e-f39e81c7a292"), 
				"Date",
				ValueTypeBuilder.text().build(),
				null,
				true,
				false);
	}

}
