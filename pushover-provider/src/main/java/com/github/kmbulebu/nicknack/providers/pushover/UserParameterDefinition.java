package com.github.kmbulebu.nicknack.providers.pushover;

import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

import com.github.kmbulebu.nicknack.core.actions.parameters.BasicParameterDefinition;
import com.github.kmbulebu.nicknack.core.units.StringUnit;

public class UserParameterDefinition extends BasicParameterDefinition<StringUnit> {

	public static final UUID DEF_UUID = UUID.fromString("b5e9afc9-950d-4f14-a60b-f609d96c055f");
	
	public static final UserParameterDefinition INSTANCE = new UserParameterDefinition();

	public UserParameterDefinition() {
		super(DEF_UUID, "User or Group Key", StringUnit.INSTANCE, true);
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
