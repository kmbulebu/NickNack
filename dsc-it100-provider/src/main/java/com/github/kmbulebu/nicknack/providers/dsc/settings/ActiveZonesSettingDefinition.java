package com.github.kmbulebu.nicknack.providers.dsc.settings;

import java.util.UUID;

import com.github.kmbulebu.nicknack.core.attributes.BasicAttributeDefinition;
import com.github.kmbulebu.nicknack.core.valuetypes.builder.ValueTypeBuilder;
import com.github.kmbulebu.nicknack.core.valuetypes.impl.wholenumber.WholeNumber;
import com.github.kmbulebu.nicknack.core.valuetypes.impl.wholenumber.WholeNumberRangeChoices;

public class ActiveZonesSettingDefinition extends BasicAttributeDefinition<WholeNumber, Integer> {
	
	public static final UUID DEF_UUID = UUID.fromString("3aba3dd9-5228-45a2-97a4-053bab71c475");

	// 
	public ActiveZonesSettingDefinition() {
		super(DEF_UUID, "Active Zones", 
			"Add a zone to NickNack. If none are specified, all zones are used.",
			ValueTypeBuilder.wholeNumber().min(1).max(16).build(),
			new WholeNumberRangeChoices(1, 16), 
			true,
			true);
	}

}
