package com.github.kmbulebu.nicknack.providers.wemo.attributes;

import java.util.UUID;

import com.github.kmbulebu.nicknack.core.attributes.BasicAttributeDefinition;
import com.github.kmbulebu.nicknack.core.units.StringUnit;

public class FriendlyNameAttributeDefinition extends BasicAttributeDefinition {
	
	public static final FriendlyNameAttributeDefinition INSTANCE = new FriendlyNameAttributeDefinition();
	
	public FriendlyNameAttributeDefinition() {
		super(UUID.fromString("6773fcbf-2225-403d-8e08-6424886193cb"), "Friendly Name", StringUnit.INSTANCE, false);
	}	
	
}
