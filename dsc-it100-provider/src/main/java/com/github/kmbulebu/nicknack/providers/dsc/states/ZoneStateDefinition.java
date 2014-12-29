package com.github.kmbulebu.nicknack.providers.dsc.states;

import java.util.UUID;

import com.github.kmbulebu.nicknack.core.states.BasicStateDefinition;
import com.github.kmbulebu.nicknack.providers.dsc.attributes.ZoneLabelAttributeDefinition;
import com.github.kmbulebu.nicknack.providers.dsc.attributes.ZoneNumberAttributeDefinition;
import com.github.kmbulebu.nicknack.providers.dsc.attributes.ZoneOpenAttributeDefinition;

public class ZoneStateDefinition extends BasicStateDefinition {
	
	public static final ZoneStateDefinition INSTANCE = new ZoneStateDefinition();

	private ZoneStateDefinition() {
		super(UUID.fromString("5d3cb7d1-d88f-4a93-97cb-c14370971fde"), 
				"Zone", 
				ZoneLabelAttributeDefinition.INSTANCE,
				ZoneNumberAttributeDefinition.INSTANCE,
				ZoneOpenAttributeDefinition.INSTANCE);
	}

}
