package com.github.kmbulebu.nicknack.core.attributes.impl;

import java.util.UUID;

import com.github.kmbulebu.nicknack.core.attributes.BasicAttributeDefinition;
import com.github.kmbulebu.nicknack.core.valuetypes.builder.ValueTypeBuilder;
import com.github.kmbulebu.nicknack.core.valuetypes.impl.booleans.OnOff;
import com.github.kmbulebu.nicknack.core.valuetypes.impl.booleans.BooleanValueChoices;

public class OnOffSwitchAttributeDefinition extends BasicAttributeDefinition<OnOff, Boolean> {

	public OnOffSwitchAttributeDefinition(UUID uuid, String name, boolean isRequired) {
		super(uuid, name, 
				ValueTypeBuilder.onOff().build(),
				new BooleanValueChoices(),
				isRequired,
				false);
	}
	
}
