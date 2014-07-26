package com.oakcity.nicknack.providers.dsc.events;

import java.util.UUID;

import com.oakcity.nicknack.core.events.BasicEventDefinition;

public class PartitionDisarmedEventDefinition extends BasicEventDefinition {
	
	public static final PartitionDisarmedEventDefinition INSTANCE = new PartitionDisarmedEventDefinition();

	private PartitionDisarmedEventDefinition() {
		super(UUID.fromString("17f067ab-9297-4d51-a936-3849c122d6b8"), 
				"Partition Disarmed", 
				PartitionLabelAttributeDefinition.INSTANCE,
				PartitionNumberAttributeDefinition.INSTANCE);
	}

}
