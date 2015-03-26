package com.github.kmbulebu.nicknack.core.attributes.impl;

import java.util.UUID;

import com.github.kmbulebu.nicknack.core.attributes.BasicAttributeDefinition;
import com.github.kmbulebu.nicknack.core.valuetypes.builder.ValueTypeBuilder;
import com.github.kmbulebu.nicknack.core.valuetypes.impl.text.Text;

public class MacAddressAttributeDefinition extends BasicAttributeDefinition<Text, String> {
	
	private static final String REGEX = "[0-9a-fA-F:]";
	private static final String REGEX_ERROR = "Must contain only digits, A-F, and colons.";

	public MacAddressAttributeDefinition(UUID uuid, String name, boolean isRequired) {
		super(uuid, 
				name, 
				ValueTypeBuilder.text().minLength(12).maxLength(17).regEx(REGEX, REGEX_ERROR).build(),
				null,
				isRequired,
				false);
	}	
}
