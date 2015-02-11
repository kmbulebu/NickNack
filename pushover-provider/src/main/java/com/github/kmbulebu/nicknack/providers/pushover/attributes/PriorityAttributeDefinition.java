package com.github.kmbulebu.nicknack.providers.pushover.attributes;

import java.util.UUID;

import com.github.kmbulebu.nicknack.core.attributes.BasicAttributeDefinition;
import com.github.kmbulebu.nicknack.core.valuetypes.WholeNumberType;

public class PriorityAttributeDefinition extends BasicAttributeDefinition<WholeNumberType> {

	public static final UUID DEF_UUID = UUID.fromString("132d58c5-325c-43d1-8539-9a356898755d");
	
	public static final PriorityAttributeDefinition INSTANCE = new PriorityAttributeDefinition();

	public PriorityAttributeDefinition() {
		super(DEF_UUID, "Priority", new WholeNumberType(-2, 2, 1), false);
	}

}
