package com.oakcity.nicknack.providers.dsc.events;

import java.util.UUID;

import com.oakcity.nicknack.core.events.BasicAttributeDefinition;
import com.oakcity.nicknack.core.units.StringUnit;

public class PartitionLabelAttributeDefinition extends BasicAttributeDefinition {
	
	public static final PartitionLabelAttributeDefinition INSTANCE = new PartitionLabelAttributeDefinition();

	public PartitionLabelAttributeDefinition() {
		super(UUID.fromString("639d4cf6-48a3-4979-88dd-94802b19b2b1"), "Partition Label", StringUnit.INSTANCE, true);
	}

}
