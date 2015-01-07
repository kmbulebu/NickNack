package com.github.kmbulebu.nicknack.providers.wemo.actions;

import java.util.UUID;

import com.github.kmbulebu.nicknack.core.attributes.BasicAttributeDefinition;
import com.github.kmbulebu.nicknack.core.units.StringUnit;

public class FriendlyNameAttributeDefinition extends BasicAttributeDefinition {
	
	public static final FriendlyNameAttributeDefinition INSTANCE = new FriendlyNameAttributeDefinition();
	
	public FriendlyNameAttributeDefinition() {
		super(UUID.fromString("06c6845b-9804-4cd9-821b-d8a394d50332"), "Friendly Name", StringUnit.INSTANCE, true);
	}

}
