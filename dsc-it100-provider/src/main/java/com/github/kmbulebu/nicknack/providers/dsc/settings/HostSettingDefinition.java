package com.github.kmbulebu.nicknack.providers.dsc.settings;

import java.util.List;

import com.github.kmbulebu.nicknack.core.providers.settings.AbstractProviderHostNameSettingDefinition;

public class HostSettingDefinition extends AbstractProviderHostNameSettingDefinition {
	
	public HostSettingDefinition() {
		super("host",
			"Hostname", 
			"Hostname of system with IT-100",
			true);
	}

	@Override
	public boolean isValid(String value) {
		return super.isValid(value);
	}

	@Override
	public List<String> getValueChoices() {
		return null;
	}

}
