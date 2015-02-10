package com.github.kmbulebu.nicknack.providers.dsc.attributes;

import java.util.UUID;

import com.github.kmbulebu.nicknack.core.attributes.BasicAttributeDefinition;
import com.github.kmbulebu.nicknack.core.valuetypes.WholeNumberType;

public class PgmNumberAttributeDefinition extends BasicAttributeDefinition<WholeNumberType> {
	
	public static final UUID DEF_UUID = UUID.fromString("b60f1f17-4024-40b3-921f-e00ce0790565");
	
	public static final PgmNumberAttributeDefinition INSTANCE = new PgmNumberAttributeDefinition();
	
	public PgmNumberAttributeDefinition() {
		super(DEF_UUID, "Pgm Output", new WholeNumberType(1, 4, 1), true);
	}

/*	@Override
	public String format(String rawValue) {
		return rawValue;
	}
	
	private static final String VALIDATE_ERROR = "PGM must be an integer number between 1 and 4.";

	@Override
	public Collection<String> validate(String value) {
		if (!value.matches("\\d+")) {
			return Collections.singletonList(VALIDATE_ERROR);
		}
		final int intValue = Integer.parseInt(value);
		
		if (intValue < 1 || intValue > 4) {
			return Collections.singletonList(VALIDATE_ERROR);
		}
		return null;
	}*/

}
