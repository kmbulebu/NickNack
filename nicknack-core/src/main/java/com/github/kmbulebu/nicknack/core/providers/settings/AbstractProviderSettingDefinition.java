package com.github.kmbulebu.nicknack.core.providers.settings;


public abstract class AbstractProviderSettingDefinition<ValueType> implements ProviderSettingDefinition<ValueType> {
	
	private final String key;
	private final String name;
	private final String description;
	private final boolean isRequired;
	
	public AbstractProviderSettingDefinition(String key, String name, String description, boolean isRequired) {
		super();
		this.key = key;
		this.name = name;
		this.description = description;
		this.isRequired = isRequired;
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

}
