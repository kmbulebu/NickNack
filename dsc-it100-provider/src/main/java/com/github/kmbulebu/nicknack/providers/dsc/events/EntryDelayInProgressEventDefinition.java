package com.github.kmbulebu.nicknack.providers.dsc.events;

import java.util.UUID;

import com.github.kmbulebu.nicknack.core.events.impl.BasicTimestampedEventDefinition;
import com.github.kmbulebu.nicknack.providers.dsc.attributes.PartitionLabelAttributeDefinition;
import com.github.kmbulebu.nicknack.providers.dsc.attributes.PartitionNumberAttributeDefinition;

public class EntryDelayInProgressEventDefinition extends BasicTimestampedEventDefinition {
	
	public static final EntryDelayInProgressEventDefinition INSTANCE = new EntryDelayInProgressEventDefinition();

	private EntryDelayInProgressEventDefinition() {
		super(UUID.fromString("79341832-d336-49b0-a208-742a23236426"), 
				"Entry Delay in Progress", 
				PartitionLabelAttributeDefinition.INSTANCE,
				PartitionNumberAttributeDefinition.INSTANCE);
	}

}
