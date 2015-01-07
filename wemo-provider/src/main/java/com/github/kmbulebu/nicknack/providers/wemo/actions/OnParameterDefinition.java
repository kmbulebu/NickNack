package com.github.kmbulebu.nicknack.providers.wemo.actions;

import java.util.UUID;

import com.github.kmbulebu.nicknack.core.attributes.impl.OnOffSwitchParameterDefinition;

public class OnParameterDefinition extends OnOffSwitchParameterDefinition {
	
	public static final OnParameterDefinition INSTANCE = new OnParameterDefinition();

	public OnParameterDefinition() {
		super(UUID.fromString("768f61c5-1c11-4588-8d75-4a049131a95c"), "On", true);
	}

}
