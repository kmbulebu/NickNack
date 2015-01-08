package com.github.kmbulebu.nicknack.core.attributes.impl;

import java.util.UUID;

import com.github.kmbulebu.nicknack.core.attributes.BasicAttributeDefinition;
import com.github.kmbulebu.nicknack.core.units.BooleanUnit;

public class OnOffSwitchAttributeDefinition extends BasicAttributeDefinition {

	public OnOffSwitchAttributeDefinition(UUID uuid, String name, boolean isRequired) {
		super(uuid, name, BooleanUnit.INSTANCE, isRequired);
	}

	/*@Override
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
	}*/
	
	

}
