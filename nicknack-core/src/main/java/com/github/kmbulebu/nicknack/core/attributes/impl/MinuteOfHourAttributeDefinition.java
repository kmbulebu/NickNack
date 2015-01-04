package com.github.kmbulebu.nicknack.core.attributes.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.github.kmbulebu.nicknack.core.attributes.BasicAttributeDefinition;
import com.github.kmbulebu.nicknack.core.units.IntegerUnit;

public class MinuteOfHourAttributeDefinition extends BasicAttributeDefinition{
	
	public static final MinuteOfHourAttributeDefinition INSTANCE = new MinuteOfHourAttributeDefinition();

	public MinuteOfHourAttributeDefinition() {
		super(UUID.fromString("d07bf3e7-bda0-48ac-a6b9-f88a11495b49"), "Minute of Hour", IntegerUnit.INSTANCE, true);
	}
	
	public Map<String, String> getStaticValues() {
		final Map<String, String> values = new HashMap<>();
		for (int i = 0; i < 60 ; i++) {
			values.put(Integer.toString(i), Integer.toString(i));
		}
		return values;
	}

}
