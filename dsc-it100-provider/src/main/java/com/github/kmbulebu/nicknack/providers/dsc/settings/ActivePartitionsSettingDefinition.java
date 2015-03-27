package com.github.kmbulebu.nicknack.providers.dsc.settings;

import java.util.UUID;

import com.github.kmbulebu.nicknack.core.attributes.BasicAttributeDefinition;
import com.github.kmbulebu.nicknack.core.valuetypes.builder.ValueTypeBuilder;
import com.github.kmbulebu.nicknack.core.valuetypes.impl.wholenumber.WholeNumber;
import com.github.kmbulebu.nicknack.core.valuetypes.impl.wholenumber.WholeNumberRangeChoices;

public class ActivePartitionsSettingDefinition extends BasicAttributeDefinition<WholeNumber, Integer> {
	
	public static final UUID DEF_UUID = UUID.fromString("ee116507-18c1-4dde-8ebb-592adaf1878e");

	public ActivePartitionsSettingDefinition() {
		super(DEF_UUID, "Active Partitions", 
				"Add a partition to NickNack. If none are specified, all partitions are used.", 
				ValueTypeBuilder.wholeNumber().min(1).max(16).build(),
				new WholeNumberRangeChoices(1, 16), 
				true,
				true);
	}


}
