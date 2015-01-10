package com.github.kmbulebu.nicknack.core.providers;

import java.util.List;

import com.github.kmbulebu.nicknack.core.providers.settings.ProviderSettingDefinition;

public interface ProviderConfiguration {

	public <ValueType> ValueType getValue(ProviderSettingDefinition<ValueType> settingDefinition);
	
	public <ValueType> ValueType getValue(ProviderSettingDefinition<ValueType> settingDefinition, ValueType defaultValue);
	
	public <ValueType> List<ValueType> getValues(ProviderSettingDefinition<ValueType> settingDefinition);
	
	/*public <ValueType> void putValue(ProviderSettingDefinition<ValueType> settingDefinition, ValueType value);
	
	public <ValueType> void addValue(ProviderMultiValueSettingDefinition<ValueType> settingDefinition, ValueType value);*/
	
}
