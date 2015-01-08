package com.github.kmbulebu.nicknack.providers.wemo.attributes;

import java.util.UUID;

import com.github.kmbulebu.nicknack.core.attributes.impl.OnOffSwitchAttributeDefinition;

public class OnAttributeDefinition extends OnOffSwitchAttributeDefinition {
	
	public static final OnAttributeDefinition INSTANCE = new OnAttributeDefinition();

	public OnAttributeDefinition() {
		super(UUID.fromString("768f61c5-1c11-4588-8d75-4a049131a95c"), "On", true);
	}

}
