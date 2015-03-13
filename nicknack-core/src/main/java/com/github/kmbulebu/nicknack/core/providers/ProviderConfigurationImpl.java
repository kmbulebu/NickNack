package com.github.kmbulebu.nicknack.core.providers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.kmbulebu.nicknack.core.providers.settings.SettingDefinition;
import com.github.kmbulebu.nicknack.core.valuetypes.ValueType;

public class ProviderConfigurationImpl implements ProviderConfiguration {
	
	private final Map<String, List<String>> keyToValuesMap = new HashMap<>();
	private Map<String, List<String>> errors = new HashMap<>();
	private boolean complete;
	private boolean enabled;

	@Override
	public <T extends ValueType> String getValue(SettingDefinition<T> settingDefinition) {
		final List<String> values = getValues(settingDefinition);
		if (values == null) {
			return null;
		} else {
			return values.get(0);
		}
	}

	@Override
	public <T extends ValueType> String getValue(SettingDefinition<T> settingDefinition,
			String defaultValue) {
		final List<String> values = getValues(settingDefinition);
		if (values == null || values.get(0) == null) {
			return defaultValue;
		} else {
			return values.get(0);
		}
	}

	@Override
	public <T extends ValueType> List<String> getValues(
			SettingDefinition<T> settingDefinition) {
		return keyToValuesMap.get(settingDefinition.getKey());
	}
	
	protected <T extends ValueType> void setValues(SettingDefinition<T> settingDefinition, List<String> values) {
		keyToValuesMap.put(settingDefinition.getKey(), values);
	}
	
	protected void setAllValues(Map<String, List<String>> values) {
		keyToValuesMap.clear();
		keyToValuesMap.putAll(values);
	}
	
	@Override
	public Map<String, List<String>> getErrors() {
		return errors;
	}
	
	@Override
	public boolean isComplete() {
		return complete;
	}
	
	protected void setComplete(boolean complete) {
		this.complete = complete;
	}
	
	@Override
	public boolean isEnabled() {
		return enabled;
	}
	
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	public void addError(String key, String message) {
		List<String> fieldErrors = errors.get(key);
		
		if (fieldErrors == null) {
			fieldErrors = new ArrayList<>();
			errors.put(key, fieldErrors);
		}
		
		fieldErrors.add(message);
	}

}
