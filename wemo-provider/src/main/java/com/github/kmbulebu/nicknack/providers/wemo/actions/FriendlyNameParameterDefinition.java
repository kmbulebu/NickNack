package com.github.kmbulebu.nicknack.providers.wemo.actions;

import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

import com.github.kmbulebu.nicknack.core.actions.parameters.BasicParameterDefinition;
import com.github.kmbulebu.nicknack.core.units.StringUnit;

public class FriendlyNameParameterDefinition extends BasicParameterDefinition<StringUnit> {
	
	public static final FriendlyNameParameterDefinition INSTANCE = new FriendlyNameParameterDefinition();
	
	public FriendlyNameParameterDefinition() {
		super(UUID.fromString("06c6845b-9804-4cd9-821b-d8a394d50332"), "Friendly Name", StringUnit.INSTANCE, true);
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
