package com.oakcity.nicknack.core.events.impl;

import java.util.UUID;

import com.oakcity.nicknack.core.events.BasicAttributeDefinition;
import com.oakcity.nicknack.core.units.IntegerUnit;

public class MinuteOfHourAttributeDefinition extends BasicAttributeDefinition{
	
	public static final MinuteOfHourAttributeDefinition INSTANCE = new MinuteOfHourAttributeDefinition();

	public MinuteOfHourAttributeDefinition() {
		super(UUID.fromString("d07bf3e7-bda0-48ac-a6b9-f88a11495b49"), "Minute of Hour", IntegerUnit.INSTANCE, true);
	}

}
