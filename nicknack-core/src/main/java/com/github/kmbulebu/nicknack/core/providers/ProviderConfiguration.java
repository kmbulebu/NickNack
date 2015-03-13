package com.github.kmbulebu.nicknack.core.providers;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.github.kmbulebu.nicknack.core.providers.settings.SettingDefinition;
import com.github.kmbulebu.nicknack.core.providers.settings.SettingType;

public interface ProviderConfiguration {

	public <T extends SettingType<U>,U extends Serializable> U getValue(SettingDefinition<T,U> settingDefinition);
	
	public <T extends SettingType<U>,U extends Serializable> U getValue(SettingDefinition<T,U> settingDefinition, U defaultValue);
	
	public <T extends SettingType<U>,U extends Serializable> List<U> getValues(SettingDefinition<T,U> settingDefinition);
	
	public boolean isComplete();
	
	public boolean isEnabled();
	
	public Map<String, List<String>> getErrors();
	
}
