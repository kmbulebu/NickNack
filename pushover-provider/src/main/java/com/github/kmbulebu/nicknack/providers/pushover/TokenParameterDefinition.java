package com.github.kmbulebu.nicknack.providers.pushover;

import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

import com.github.kmbulebu.nicknack.core.actions.parameters.BasicParameterDefinition;
import com.github.kmbulebu.nicknack.core.units.StringUnit;

public class TokenParameterDefinition extends BasicParameterDefinition<StringUnit> {

	public static final UUID DEF_UUID = UUID.fromString("85cef8e1-5438-49dd-aacb-627c15515d55");
	
	public static final TokenParameterDefinition INSTANCE = new TokenParameterDefinition();

	public TokenParameterDefinition() {
		super(DEF_UUID, "API Token", StringUnit.INSTANCE, true);
	}

	@Override
	public String format(String rawValue) {
		return rawValue;
	}

	@Override
	public Collection<String> validate(String value) {
		return Collections.emptyList();
	}
	
	

}
