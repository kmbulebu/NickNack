package com.github.kmbulebu.nicknack.providers.dsc.attributes;

import java.util.UUID;

import com.github.kmbulebu.nicknack.core.attributes.BasicAttributeDefinition;
import com.github.kmbulebu.nicknack.core.units.IntegerUnit;

public class PartitionNumberAttributeDefinition extends BasicAttributeDefinition {
	
	public static final PartitionNumberAttributeDefinition INSTANCE = new PartitionNumberAttributeDefinition();

	public PartitionNumberAttributeDefinition() {
		super(UUID.fromString("d560bb13-349c-4513-8872-e8d82ed0b144"), "Partition Number", IntegerUnit.INSTANCE, false);
	}

}
