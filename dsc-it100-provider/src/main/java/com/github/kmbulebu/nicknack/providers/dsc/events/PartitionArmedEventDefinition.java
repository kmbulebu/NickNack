package com.github.kmbulebu.nicknack.providers.dsc.events;

import java.util.UUID;

import com.github.kmbulebu.nicknack.core.events.impl.BasicTimestampedEventDefinition;
import com.github.kmbulebu.nicknack.providers.dsc.attributes.PartitionArmedModeAttributeDefinition;
import com.github.kmbulebu.nicknack.providers.dsc.attributes.PartitionLabelAttributeDefinition;
import com.github.kmbulebu.nicknack.providers.dsc.attributes.PartitionNumberAttributeDefinition;

public class PartitionArmedEventDefinition extends BasicTimestampedEventDefinition {
	
	public static final PartitionArmedEventDefinition INSTANCE = new PartitionArmedEventDefinition();

	private PartitionArmedEventDefinition() {
		super(UUID.fromString("c2046f2a-9aa4-4360-9dc6-a83f56a27457"), 
				"Partition Armed", 
				PartitionLabelAttributeDefinition.INSTANCE,
				PartitionNumberAttributeDefinition.INSTANCE,
				PartitionArmedModeAttributeDefinition.INSTANCE);
	}

}
