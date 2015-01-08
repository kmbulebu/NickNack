package com.github.kmbulebu.nicknack.providers.pushover.attributes;

import java.util.UUID;

import com.github.kmbulebu.nicknack.core.attributes.BasicAttributeDefinition;
import com.github.kmbulebu.nicknack.core.units.StringUnit;

public class TokenAttributeDefinition extends BasicAttributeDefinition {

	public static final UUID DEF_UUID = UUID.fromString("85cef8e1-5438-49dd-aacb-627c15515d55");
	
	public static final TokenAttributeDefinition INSTANCE = new TokenAttributeDefinition();

	public TokenAttributeDefinition() {
		super(DEF_UUID, "API Token", StringUnit.INSTANCE, true);
	}

}
