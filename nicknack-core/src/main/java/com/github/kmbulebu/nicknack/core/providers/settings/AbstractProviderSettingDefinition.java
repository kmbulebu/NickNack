package com.github.kmbulebu.nicknack.core.providers.settings;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;


public abstract class AbstractProviderSettingDefinition<T extends SettingType<U>, U extends Serializable> implements SettingDefinition<T,U> {
	
	private final String key;
	private final String name;
	private final String description;
	private final boolean isRequired;
	private final boolean isArray;
	private final T settingType;
	private final List<U> choices;
	
	public AbstractProviderSettingDefinition(String key, T settingType, List<U> choices, String name, String description, boolean isRequired, boolean isArray) {
		super();
		this.key = key;
		this.name = name;
		this.description = description;
		this.isRequired = isRequired;
		this.isArray = isArray;
		this.settingType = settingType;
		if (choices == null) {
			this.choices = null;
		} else {
			this.choices = Collections.unmodifiableList(choices);
		}
	}

	@Override
	public String getKey() {
		return key;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public boolean isRequired() {
		return isRequired;
	}
	
	@Override
	public boolean isArray() {
		return isArray;
	}
	
	@Override
	public T getSettingType() {
		return settingType;
	}
	
	@Override
	public List<U> getValueChoices() {
		return choices;
	}
}
