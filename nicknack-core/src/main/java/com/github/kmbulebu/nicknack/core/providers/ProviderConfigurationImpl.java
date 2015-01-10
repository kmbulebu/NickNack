package com.github.kmbulebu.nicknack.core.providers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.kmbulebu.nicknack.core.providers.settings.ProviderSettingDefinition;

public class ProviderConfigurationImpl implements ProviderConfiguration {
	
	final Map<String, List<?>> keyToValuesMap = new HashMap<>();

	@Override
	public <ValueType> ValueType getValue(ProviderSettingDefinition<ValueType> settingDefinition) {
		final List<ValueType> values = getValues(settingDefinition);
		if (values == null) {
			return null;
		} else {
			return values.get(0);
		}
	}

	@Override
	public <ValueType> ValueType getValue(ProviderSettingDefinition<ValueType> settingDefinition, ValueType defaultValue) {
		final List<ValueType> values = getValues(settingDefinition);
		if (values == null || values.get(0) == null) {
			return defaultValue;
		} else {
			return values.get(0);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <ValueType> List<ValueType> getValues(ProviderSettingDefinition<ValueType> settingDefinition) {
		return (List<ValueType>) keyToValuesMap.get(settingDefinition.getKey());
	}
	
	public <ValueType> void setValues(ProviderSettingDefinition<ValueType> settingDefinition, List<ValueType> values) {
		keyToValuesMap.put(settingDefinition.getKey(), values);
	}

}
