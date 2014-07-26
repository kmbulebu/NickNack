package com.oakcity.nicknack.providers.dsc.actions;

import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

import com.oakcity.nicknack.core.actions.parameters.BasicParameterDefinition;
import com.oakcity.nicknack.core.units.IntegerUnit;

public class PgmNumberParameterDefinition extends BasicParameterDefinition<IntegerUnit> {
	
	public static final UUID DEF_UUID = UUID.fromString("b60f1f17-4024-40b3-921f-e00ce0790565");
	
	public static final PgmNumberParameterDefinition INSTANCE = new PgmNumberParameterDefinition();
	
	public PgmNumberParameterDefinition() {
		super(DEF_UUID, "Pgm Output", IntegerUnit.INSTANCE, true);
	}

	@Override
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
	}

}
