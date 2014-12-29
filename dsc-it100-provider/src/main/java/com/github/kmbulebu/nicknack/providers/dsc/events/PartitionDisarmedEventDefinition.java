package com.github.kmbulebu.nicknack.providers.dsc.events;

import java.util.UUID;

import com.github.kmbulebu.nicknack.core.events.impl.BasicTimestampedEventDefinition;
import com.github.kmbulebu.nicknack.providers.dsc.attributes.PartitionLabelAttributeDefinition;
import com.github.kmbulebu.nicknack.providers.dsc.attributes.PartitionNumberAttributeDefinition;

public class PartitionDisarmedEventDefinition extends BasicTimestampedEventDefinition {
	
	public static final PartitionDisarmedEventDefinition INSTANCE = new PartitionDisarmedEventDefinition();

	private PartitionDisarmedEventDefinition() {
		super(UUID.fromString("17f067ab-9297-4d51-a936-3849c122d6b8"), 
				"Partition Disarmed", 
				PartitionLabelAttributeDefinition.INSTANCE,
				PartitionNumberAttributeDefinition.INSTANCE);
	}

}
