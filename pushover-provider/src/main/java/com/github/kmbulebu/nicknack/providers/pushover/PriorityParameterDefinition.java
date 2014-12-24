package com.github.kmbulebu.nicknack.providers.pushover;

import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

import com.github.kmbulebu.nicknack.core.actions.parameters.BasicParameterDefinition;
import com.github.kmbulebu.nicknack.core.units.IntegerUnit;

public class PriorityParameterDefinition extends BasicParameterDefinition<IntegerUnit> {

	public static final UUID DEF_UUID = UUID.fromString("132d58c5-325c-43d1-8539-9a356898755d");
	
	public static final PriorityParameterDefinition INSTANCE = new PriorityParameterDefinition();

	public PriorityParameterDefinition() {
		super(DEF_UUID, "Priority", IntegerUnit.INSTANCE, false);
	}

	@Override
	public String format(String rawValue) {
		return rawValue;
	}

	@Override
	public Collection<String> validate(String value) {
		return Collections.emptyList();
	}


	

}
