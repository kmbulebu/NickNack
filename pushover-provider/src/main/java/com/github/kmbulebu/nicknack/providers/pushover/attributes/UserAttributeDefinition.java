package com.github.kmbulebu.nicknack.providers.pushover.attributes;

import java.util.UUID;

import com.github.kmbulebu.nicknack.core.attributes.BasicAttributeDefinition;
import com.github.kmbulebu.nicknack.core.units.StringUnit;

public class UserAttributeDefinition extends BasicAttributeDefinition {

	public static final UUID DEF_UUID = UUID.fromString("b5e9afc9-950d-4f14-a60b-f609d96c055f");
	
	public static final UserAttributeDefinition INSTANCE = new UserAttributeDefinition();

	public UserAttributeDefinition() {
		super(DEF_UUID, "User or Group Key", StringUnit.INSTANCE, true);
	}

}
