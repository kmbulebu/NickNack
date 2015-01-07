package com.github.kmbulebu.nicknack.providers.pushover;

import java.util.UUID;

import com.github.kmbulebu.nicknack.core.attributes.BasicAttributeDefinition;
import com.github.kmbulebu.nicknack.core.units.IntegerUnit;

public class PriorityAttributeDefinition extends BasicAttributeDefinition {

	public static final UUID DEF_UUID = UUID.fromString("132d58c5-325c-43d1-8539-9a356898755d");
	
	public static final PriorityAttributeDefinition INSTANCE = new PriorityAttributeDefinition();

	public PriorityAttributeDefinition() {
		super(DEF_UUID, "Priority", IntegerUnit.INSTANCE, false);
	}

}
