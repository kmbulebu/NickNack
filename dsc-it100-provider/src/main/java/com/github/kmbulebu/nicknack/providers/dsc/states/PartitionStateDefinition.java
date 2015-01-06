package com.github.kmbulebu.nicknack.providers.dsc.states;

import java.util.UUID;

import com.github.kmbulebu.nicknack.core.states.BasicStateDefinition;
import com.github.kmbulebu.nicknack.providers.dsc.attributes.PartitionArmedAttributeDefinition;
import com.github.kmbulebu.nicknack.providers.dsc.attributes.PartitionArmedModeAttributeDefinition;
import com.github.kmbulebu.nicknack.providers.dsc.attributes.PartitionLabelAttributeDefinition;
import com.github.kmbulebu.nicknack.providers.dsc.attributes.PartitionNumberAttributeDefinition;

public class PartitionStateDefinition extends BasicStateDefinition {
	
	public static final PartitionStateDefinition INSTANCE = new PartitionStateDefinition();

	private PartitionStateDefinition() {
		super(UUID.fromString("afe8d27d-9eb6-4c7a-a321-a0d530479998"), 
				"Partitions", 
				PartitionLabelAttributeDefinition.INSTANCE,
				PartitionNumberAttributeDefinition.INSTANCE,
				PartitionArmedAttributeDefinition.INSTANCE,
				PartitionArmedModeAttributeDefinition.INSTANCE);
	}

}
