package com.oakcity.nicknack.core.actions.parameters;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import com.oakcity.nicknack.core.units.StringUnit;

public class MacAddressParameterDefinition extends BasicParameterDefinition<StringUnit> {

	public MacAddressParameterDefinition(UUID uuid, String name, boolean isRequired) {
		super(uuid, name, StringUnit.INSTANCE, isRequired);
	}

	@Override
	public String format(String rawValue) {
		return rawValue.toLowerCase();
	}

	@Override
	public Collection<String> validate(String value) {
		final String[] split = value.split(":");
		final List<String> errors = new ArrayList<String>();
		if (split.length != 6) {
			errors.add("Must be formatted with five colons. Example: aa:bb:cc:dd:ee:ff");
		}
		
		if (!value.matches("[0-9a-fA-F:]")) {
			errors.add("Must only contain hex characters (0-9,a-f,A-F) and colons.");
		}
		
		return errors;
	}
	
	

}
