package com.github.kmbulebu.nicknack.server.model;

import java.util.List;

import org.springframework.hateoas.ResourceSupport;

import com.github.kmbulebu.nicknack.core.providers.settings.SettingDefinition;

public class ProviderSettingDefinitionsResource extends ResourceSupport {
	
	private List<? extends SettingDefinition<?, ?>> settingDefinitions;
	
	public void setSettingDefinitions(List<? extends SettingDefinition<?, ?>> settingDefinitions) {
		this.settingDefinitions = settingDefinitions;
	}
	
	public List<? extends SettingDefinition<?, ?>> getSettingDefinitions() {
		return settingDefinitions;
	}

}
