package com.github.kmbulebu.nicknack.core.actions.parameters;

import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

import com.github.kmbulebu.nicknack.core.units.BooleanUnit;

public class OnOffSwitchParameterDefinition extends BasicParameterDefinition<BooleanUnit> {

	public OnOffSwitchParameterDefinition(UUID uuid, String name, boolean isRequired) {
		super(uuid, name, BooleanUnit.INSTANCE, isRequired);
	}

	@Override
	public String format(String rawValue) {
		if (rawValue.equalsIgnoreCase("false") || rawValue.equalsIgnoreCase("off")) {
			return "Off";
		} else if (rawValue.equalsIgnoreCase("true") || rawValue.equalsIgnoreCase("on")) {
			return "On";
		}
		return null;
	}

	@Override
	public Collection<String> validate(String value) {
		if (value.length() == 0) {
			return Collections.singleton("Must not be empty");
		}
		if (value.equalsIgnoreCase("off") || value.equalsIgnoreCase("on")) {
			return Collections.emptyList();
		} else {
			return Collections.singleton("Must be one of 'On' or 'Off'");
		}
	}
	
	

}
