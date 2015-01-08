package com.github.kmbulebu.nicknack.providers.pushover.attributes;

import java.util.UUID;

import com.github.kmbulebu.nicknack.core.attributes.BasicAttributeDefinition;
import com.github.kmbulebu.nicknack.core.units.StringUnit;

public class SoundAttributeDefinition extends BasicAttributeDefinition {

	public static final UUID DEF_UUID = UUID.fromString("5275c03d-313a-4835-a13d-1d0085c1b6f1");
	
	public static final SoundAttributeDefinition INSTANCE = new SoundAttributeDefinition();

	public SoundAttributeDefinition() {
		super(DEF_UUID, "Sound", StringUnit.INSTANCE, false);
	}	

}
