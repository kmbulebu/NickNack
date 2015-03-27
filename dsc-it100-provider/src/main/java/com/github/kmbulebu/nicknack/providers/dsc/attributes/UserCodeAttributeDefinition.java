package com.github.kmbulebu.nicknack.providers.dsc.attributes;

import java.util.UUID;

import com.github.kmbulebu.nicknack.core.attributes.BasicAttributeDefinition;
import com.github.kmbulebu.nicknack.core.valuetypes.builder.ValueTypeBuilder;
import com.github.kmbulebu.nicknack.core.valuetypes.impl.text.Text;

public class UserCodeAttributeDefinition extends BasicAttributeDefinition<Text, String> {

	public static final UUID DEF_UUID = UUID.fromString("e41c0c10-1f18-471f-a0ab-888815c54adc");

	public static final UserCodeAttributeDefinition INSTANCE = new UserCodeAttributeDefinition();

	public UserCodeAttributeDefinition() {
		super(DEF_UUID, "User Code", ValueTypeBuilder.text().minLength(4).maxLength(6)
				.regEx("+\\d", "User code must be all numbers.").build(), null, true, false);
	}

}
