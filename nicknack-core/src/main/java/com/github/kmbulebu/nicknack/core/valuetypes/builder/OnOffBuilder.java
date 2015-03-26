package com.github.kmbulebu.nicknack.core.valuetypes.builder;

import com.github.kmbulebu.nicknack.core.valuetypes.impl.booleans.OnOff;

public class OnOffBuilder {

	
	protected OnOffBuilder() {
		super();
	}
	
	public OnOff build() {
		return new OnOff();
	}

}
