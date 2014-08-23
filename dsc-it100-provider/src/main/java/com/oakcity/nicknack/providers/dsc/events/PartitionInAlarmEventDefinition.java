package com.oakcity.nicknack.providers.dsc.events;

import java.util.UUID;

import com.oakcity.nicknack.core.events.impl.BasicTimestampedEventDefinition;

public class PartitionInAlarmEventDefinition extends BasicTimestampedEventDefinition {
	
	public static final PartitionInAlarmEventDefinition INSTANCE = new PartitionInAlarmEventDefinition();

	private PartitionInAlarmEventDefinition() {
		super(UUID.fromString("c8f4a32c-31fc-4c70-9f49-fa82baad7a18"), 
				"Partition in Alarm", 
				PartitionLabelAttributeDefinition.INSTANCE,
				PartitionNumberAttributeDefinition.INSTANCE);
	}

}
