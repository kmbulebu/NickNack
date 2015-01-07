package com.github.kmbulebu.nicknack.providers.dsc.actions;

import java.util.UUID;

import com.github.kmbulebu.nicknack.core.attributes.BasicAttributeDefinition;
import com.github.kmbulebu.nicknack.core.units.IntegerUnit;

public class PartitionNumberAttributeDefinition extends BasicAttributeDefinition {
	
	public static final UUID DEF_UUID = UUID.fromString("5748a2bb-8030-4e17-a325-494ad11e3893");
	
	public static final PartitionNumberAttributeDefinition INSTANCE = new PartitionNumberAttributeDefinition();
	
	public PartitionNumberAttributeDefinition() {
		super(DEF_UUID, "Partition Number", IntegerUnit.INSTANCE, true);
	}

	/*@Override
	public String format(String rawValue) {
		return rawValue;
	}
	
	private static final String VALIDATE_ERROR = "Partition must be an integer number between 1 and 8.";

	@Override
	public Collection<String> validate(String value) {
		if (!value.matches("\\d+")) {
			return Collections.singletonList(VALIDATE_ERROR);
		}
		final int intValue = Integer.parseInt(value);
		
		if (intValue < 1 || intValue > 8) {
			return Collections.singletonList(VALIDATE_ERROR);
		}
		return null;
	}*/

}
