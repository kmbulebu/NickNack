package com.github.kmbulebu.nicknack.basicproviders.clock;

import com.github.kmbulebu.nicknack.core.attributes.impl.BasicTimestampedAttributeCollection;
import com.github.kmbulebu.nicknack.core.states.State;
import com.github.kmbulebu.nicknack.core.states.StateDefinition;

public class ClockState extends BasicTimestampedAttributeCollection implements State {

	@Override
	public StateDefinition getStateDefinition() {
		return ClockStateDefinition.INSTANCE;
	}

}
