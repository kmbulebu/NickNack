package com.github.kmbulebu.nicknack.providers.dsc.events;

import java.util.UUID;

import com.github.kmbulebu.nicknack.core.events.impl.BasicTimestampedEventDefinition;
import com.github.kmbulebu.nicknack.providers.dsc.attributes.PartitionLabelAttributeDefinition;
import com.github.kmbulebu.nicknack.providers.dsc.attributes.PartitionNumberAttributeDefinition;

public class ExitDelayInProgressEventDefinition extends BasicTimestampedEventDefinition {
	
	public static final ExitDelayInProgressEventDefinition INSTANCE = new ExitDelayInProgressEventDefinition();

	private ExitDelayInProgressEventDefinition() {
		super(UUID.fromString("6a79ee26-2b29-427f-937d-c3041b717b3f"), 
				"Exit Delay in Progress", 
				PartitionLabelAttributeDefinition.INSTANCE,
				PartitionNumberAttributeDefinition.INSTANCE);
	}

}
