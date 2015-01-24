package com.github.kmbulebu.nicknack.core.attributes.impl;

import java.util.UUID;

import com.github.kmbulebu.nicknack.core.attributes.BasicAttributeDefinition;
import com.github.kmbulebu.nicknack.core.valuetypes.TextType;

public class MacAddressAttributeDefinition extends BasicAttributeDefinition<TextType> {

	public MacAddressAttributeDefinition(UUID uuid, String name, boolean isRequired) {
		super(uuid, name, new TextType(), isRequired);
	}
	
	

/*	@Override
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
	}*/
	
	

}
