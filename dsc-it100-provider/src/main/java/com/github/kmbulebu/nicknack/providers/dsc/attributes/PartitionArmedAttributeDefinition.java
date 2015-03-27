package com.github.kmbulebu.nicknack.providers.dsc.attributes;

import java.util.UUID;

import com.github.kmbulebu.nicknack.core.attributes.BasicAttributeDefinition;
import com.github.kmbulebu.nicknack.core.valuetypes.builder.ValueTypeBuilder;
import com.github.kmbulebu.nicknack.core.valuetypes.impl.booleans.BooleanValueChoices;
import com.github.kmbulebu.nicknack.core.valuetypes.impl.booleans.YesNo;

public class PartitionArmedAttributeDefinition extends BasicAttributeDefinition<YesNo, Boolean> {
	
	public static final PartitionArmedAttributeDefinition INSTANCE = new PartitionArmedAttributeDefinition();

	public PartitionArmedAttributeDefinition() {
		super(UUID.fromString("742f44e0-9fe3-4f17-846d-02d07ac7448a"), "Armed",ValueTypeBuilder.yesNo().build(),
				new BooleanValueChoices(), 
				true,
				false);
	}

}
