package com.github.kmbulebu.nicknack.core.providers.settings;

import java.util.ArrayList;
import java.util.List;


public abstract class AbstractProviderSettingDefinition<ValueType> implements ProviderSettingDefinition<ValueType> {
	
	private final String key;
	private final String name;
	private final String description;
	private final boolean isRequired;
	private final boolean isArray;
	
	public AbstractProviderSettingDefinition(String key, String name, String description, boolean isRequired, boolean isArray) {
		super();
		this.key = key;
		this.name = name;
		this.description = description;
		this.isRequired = isRequired;
		this.isArray = isArray;
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
	public List<String> save(List<ValueType> settingValues) {
		final List<String> stringList = new ArrayList<>(settingValues.size());
		for (int i = 0 ; i < settingValues.size() ; i++) {
			stringList.add(i, save(settingValues.get(i)));
		}
		return stringList;
	}

	@Override
	public List<ValueType> load(List<String> savedData) {
		final List<ValueType> valueList = new ArrayList<ValueType>(savedData.size());
		for (int i = 0; i < savedData.size(); i++) {
			valueList.add(i, load(savedData.get(i)));
		}
		return valueList;
	}

}
