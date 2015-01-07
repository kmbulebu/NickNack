package com.github.kmbulebu.nicknack.providers.dsc.actions;

import java.util.UUID;

import com.github.kmbulebu.nicknack.core.attributes.BasicAttributeDefinition;
import com.github.kmbulebu.nicknack.core.units.StringUnit;

public class UserCodeAttributeDefinition extends BasicAttributeDefinition {
	
	public static final UUID DEF_UUID = UUID.fromString("e41c0c10-1f18-471f-a0ab-888815c54adc");
	
	public static final UserCodeAttributeDefinition INSTANCE = new UserCodeAttributeDefinition();
	
	public UserCodeAttributeDefinition() {
		super(DEF_UUID, "User Code", StringUnit.INSTANCE, true);
	}

/*	@Override
	public String format(String rawValue) {
		return rawValue;
	}
	
	private static final String VALIDATE_ERROR = "User code must be 4 or 6 numbers long.";

	@Override
	public Collection<String> validate(String value) {
		if (!value.matches("\\d+")) {
			return Collections.singletonList(VALIDATE_ERROR);
		}
		
		if (value.length() != 4 || value.length() != 6) {
			return Collections.singletonList(VALIDATE_ERROR);
		}
		return null;
	}*/

}
