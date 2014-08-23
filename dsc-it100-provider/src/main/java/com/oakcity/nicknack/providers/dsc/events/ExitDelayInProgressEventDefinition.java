package com.oakcity.nicknack.providers.dsc.events;

import java.util.UUID;

import com.oakcity.nicknack.core.events.impl.BasicTimestampedEventDefinition;

public class ExitDelayInProgressEventDefinition extends BasicTimestampedEventDefinition {
	
	public static final ExitDelayInProgressEventDefinition INSTANCE = new ExitDelayInProgressEventDefinition();

	private ExitDelayInProgressEventDefinition() {
		super(UUID.fromString("6a79ee26-2b29-427f-937d-c3041b717b3f"), 
				"Exit Delay in Progress", 
				PartitionLabelAttributeDefinition.INSTANCE,
				PartitionNumberAttributeDefinition.INSTANCE);
	}

}
