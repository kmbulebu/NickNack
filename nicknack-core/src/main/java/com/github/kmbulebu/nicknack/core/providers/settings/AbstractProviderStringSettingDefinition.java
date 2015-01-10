package com.github.kmbulebu.nicknack.core.providers.settings;


public abstract class AbstractProviderStringSettingDefinition extends AbstractProviderSettingDefinition<String> {

	public AbstractProviderStringSettingDefinition(String key, String name, String description, boolean isRequired, boolean isArray) {
		super(key, name, description, isRequired, isArray);
	}

	@Override
	public String save(String settingValue) {
		return settingValue;
	}

	@Override
	public String load(String savedData) {
		return savedData;
	}

}
