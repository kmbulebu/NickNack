package com.github.kmbulebu.nicknack.providers.dsc.attributes;

import java.util.UUID;

import com.github.kmbulebu.nicknack.core.attributes.BasicAttributeDefinition;
import com.github.kmbulebu.nicknack.core.valuetypes.CheckboxType;

public class PartitionArmedAttributeDefinition extends BasicAttributeDefinition<CheckboxType> {
	
	public static final PartitionArmedAttributeDefinition INSTANCE = new PartitionArmedAttributeDefinition();

	public PartitionArmedAttributeDefinition() {
		super(UUID.fromString("742f44e0-9fe3-4f17-846d-02d07ac7448a"), "Armed", new CheckboxType(), false);
	}

}
