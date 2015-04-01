package com.github.kmbulebu.nicknack.core.valuetypes;

import java.util.List;

import com.github.kmbulebu.nicknack.core.providers.Provider;

public interface ValueChoices<T> {

	public List<T> getValueChoices(Provider provider);
	
}
