package com.github.kmbulebu.nicknack.core.valuetypes.impl.booleans;

import com.github.kmbulebu.nicknack.core.valuetypes.impl.StaticValueChoices;

public class BooleanValueChoices extends StaticValueChoices<Boolean> {
	
	public BooleanValueChoices() {
		super(new Boolean[]{Boolean.TRUE, Boolean.FALSE});
	}


}
