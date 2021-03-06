package com.github.kmbulebu.nicknack.providers.xbmc.settings;

import java.util.List;

import com.github.kmbulebu.nicknack.core.providers.settings.AbstractProviderHostNameSettingDefinition;

public class HostsSettingDefinition extends AbstractProviderHostNameSettingDefinition {
	
	public HostsSettingDefinition() {
		super("host",
			"Kodi Hostname", 
			"Hostname or IP address of Kodi.",
			true,
			true);
	}

	@Override
	public boolean isValid(String value) {
		// TODO Possibly connect and check version.
		return super.isValid(value);
	}

	@Override
	public List<String> getValueChoices() {
		return null;
	}

}
