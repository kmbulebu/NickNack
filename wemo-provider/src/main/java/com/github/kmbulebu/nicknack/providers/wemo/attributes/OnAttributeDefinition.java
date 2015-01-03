package com.github.kmbulebu.nicknack.providers.wemo.attributes;

import java.util.UUID;

import com.github.kmbulebu.nicknack.core.attributes.BasicAttributeDefinition;
import com.github.kmbulebu.nicknack.core.units.BooleanUnit;

public class OnAttributeDefinition extends BasicAttributeDefinition {
	
	public static final OnAttributeDefinition INSTANCE = new OnAttributeDefinition();
	
	public OnAttributeDefinition() {
		super(UUID.fromString("9ed426d0-687b-4765-8097-0c2688de030c"), "is On", BooleanUnit.INSTANCE, false);
	}	
	
}
