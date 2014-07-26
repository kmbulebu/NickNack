package com.oakcity.nicknack.providers.dsc.events;

import java.util.UUID;

import com.oakcity.nicknack.core.events.BasicEventDefinition;

public class PartitionArmedEventDefinition extends BasicEventDefinition {
	
	public static final PartitionArmedEventDefinition INSTANCE = new PartitionArmedEventDefinition();

	private PartitionArmedEventDefinition() {
		super(UUID.fromString("c2046f2a-9aa4-4360-9dc6-a83f56a27457"), 
				"Partition Armed", 
				PartitionLabelAttributeDefinition.INSTANCE,
				PartitionNumberAttributeDefinition.INSTANCE,
				PartitionArmedModeAttributeDefinition.INSTANCE);
	}

}
