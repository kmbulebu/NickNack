package com.github.kmbulebu.nicknack.core.valuetypes.builder;

import com.github.kmbulebu.nicknack.core.valuetypes.impl.wholenumber.WholeNumber;

public class WholeNumberBuilder {
	
	private Integer minValue = null;
	private Integer maxValue = null;
	
	protected WholeNumberBuilder() {
		super();
	}
	
	public WholeNumber build() {
		return new WholeNumber(minValue, maxValue);
	}
	
	public WholeNumberBuilder min(Integer min) {
		minValue = min;
		return this;
	}
	
	public WholeNumberBuilder max(Integer max) {
		maxValue = max;
		return this;
	}

}
