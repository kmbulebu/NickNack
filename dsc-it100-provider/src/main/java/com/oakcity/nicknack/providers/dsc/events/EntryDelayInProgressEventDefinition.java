package com.oakcity.nicknack.providers.dsc.events;

import java.util.UUID;

import com.oakcity.nicknack.core.events.BasicEventDefinition;

public class EntryDelayInProgressEventDefinition extends BasicEventDefinition {
	
	public static final EntryDelayInProgressEventDefinition INSTANCE = new EntryDelayInProgressEventDefinition();

	private EntryDelayInProgressEventDefinition() {
		super(UUID.fromString("79341832-d336-49b0-a208-742a23236426"), 
				"Entry Delay in Progress", 
				PartitionLabelAttributeDefinition.INSTANCE,
				PartitionNumberAttributeDefinition.INSTANCE);
	}

}
