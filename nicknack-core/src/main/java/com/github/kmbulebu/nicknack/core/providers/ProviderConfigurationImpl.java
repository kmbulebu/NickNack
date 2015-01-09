package com.github.kmbulebu.nicknack.core.providers;

import java.util.List;

import com.github.kmbulebu.nicknack.core.providers.settings.ProviderMultiValueSettingDefinition;
import com.github.kmbulebu.nicknack.core.providers.settings.ProviderSettingDefinition;

public class ProviderConfigurationImpl implements ProviderConfiguration {
	
	// TODO Create some maps to store our values in

	@Override
	public <ValueType> ValueType getValue(ProviderSettingDefinition<ValueType> settingDefinition) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <ValueType> ValueType getValue(ProviderSettingDefinition<ValueType> settingDefinition, ValueType defaultValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <ValueType> List<ValueType> getValues(ProviderMultiValueSettingDefinition<ValueType> settingDefinition) {
		// TODO Auto-generated method stub
		return null;
	}
	
	// TODO Create some setters for the UI to use.

}
