package com.github.kmbulebu.nicknack.core.attributes.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.github.kmbulebu.nicknack.core.attributes.BasicAttributeDefinition;
import com.github.kmbulebu.nicknack.core.valuetypes.WholeNumberType;

public class SecondOfMinuteAttributeDefinition extends BasicAttributeDefinition<WholeNumberType, Integer>{
	
	public static final SecondOfMinuteAttributeDefinition INSTANCE = new SecondOfMinuteAttributeDefinition();

	public SecondOfMinuteAttributeDefinition() {
		super(UUID.fromString("c59e38a0-ecaa-4fe1-929c-b1641459c23a"), "Second of Minute", new WholeNumberType(0, 59, 1), true);
	}
	
	public Map<String, String> getStaticValues() {
		final Map<String, String> values = new HashMap<>();
		for (int i = 0; i < 60 ; i++) {
			values.put(Integer.toString(i), Integer.toString(i));
		}
		return values;
	}

}
