package com.github.kmbulebu.nicknack.core.events.impl;

import java.util.UUID;

import com.github.kmbulebu.nicknack.core.events.BasicAttributeDefinition;
import com.github.kmbulebu.nicknack.core.units.IntegerUnit;

public class SecondOfMinuteAttributeDefinition extends BasicAttributeDefinition{
	
	public static final SecondOfMinuteAttributeDefinition INSTANCE = new SecondOfMinuteAttributeDefinition();

	public SecondOfMinuteAttributeDefinition() {
		super(UUID.fromString("c59e38a0-ecaa-4fe1-929c-b1641459c23a"), "Second of Minute", IntegerUnit.INSTANCE, true);
	}

}
