package com.github.kmbulebu.nicknack.core.valuetypes.impl.booleans;

import com.github.kmbulebu.nicknack.core.valuetypes.impl.StaticValueChoices;

public class OnOffValueChoices extends StaticValueChoices<Boolean> {
	
	public OnOffValueChoices() {
		super(new Boolean[]{Boolean.TRUE, Boolean.FALSE});
	}


}
