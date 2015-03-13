package com.github.kmbulebu.nicknack.core.providers;

import java.util.List;
import java.util.Map;

import com.github.kmbulebu.nicknack.core.providers.settings.SettingDefinition;
import com.github.kmbulebu.nicknack.core.valuetypes.ValueType;

public interface ProviderConfiguration {

	public <T extends ValueType> String getValue(SettingDefinition<T> settingDefinition);
	
	public <T extends ValueType> String  getValue(SettingDefinition<T> settingDefinition, String defaultValue);
	
	public <T extends ValueType> List<String> getValues(SettingDefinition<T> settingDefinition);
	
	public boolean isComplete();
	
	public boolean isEnabled();
	
	public Map<String, List<String>> getErrors();
	
}
