package com.github.kmbulebu.nicknack.core.providers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.kmbulebu.nicknack.core.providers.settings.SettingDefinition;
import com.github.kmbulebu.nicknack.core.providers.settings.SettingType;

public class ProviderConfigurationImpl implements ProviderConfiguration {
	
	private final Map<String, List<?>> keyToValuesMap = new HashMap<>();
	private Map<String, List<String>> errors = new HashMap<>();
	private boolean complete;
	private boolean enabled;

	@Override
	public <T extends SettingType<U>, U extends Serializable> U getValue(SettingDefinition<T, U> settingDefinition) {
		final List<U> values = getValues(settingDefinition);
		if (values == null) {
			return null;
		} else {
			return values.get(0);
		}
	}

	@Override
	public <T extends SettingType<U>, U extends Serializable> U getValue(SettingDefinition<T, U> settingDefinition,
			U defaultValue) {
		final List<U> values = getValues(settingDefinition);
		if (values == null || values.get(0) == null) {
			return defaultValue;
		} else {
			return values.get(0);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends SettingType<U>, U extends Serializable> List<U> getValues(
			SettingDefinition<T, U> settingDefinition) {
		return (List<U>) keyToValuesMap.get(settingDefinition.getKey());
	}
	
	protected <T extends SettingType<U>, U extends Serializable> void setValues(SettingDefinition<T, U> settingDefinition, List<U> values) {
		keyToValuesMap.put(settingDefinition.getKey(), values);
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
