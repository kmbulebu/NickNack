package com.github.kmbulebu.nicknack.core.valuetypes.impl;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.github.kmbulebu.nicknack.core.valuetypes.ValueChoices;

public class StaticValueChoices<T> implements ValueChoices<T> {
	
	private final List<T> valueChoices;
	
	public StaticValueChoices(List<T> values) {
		this.valueChoices = Collections.unmodifiableList(values);
	}

	public StaticValueChoices(T[] values) {
		this.valueChoices = Collections.unmodifiableList(Arrays.asList(values));
	}

	@Override
	public List<T> getValueChoices() {
		return valueChoices;
	}

}
