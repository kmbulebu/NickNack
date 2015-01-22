package com.github.kmbulebu.nicknack.providers.xbmc.settings;

import valuetypes.HostnameType;

import com.github.kmbulebu.nicknack.core.providers.settings.AbstractProviderSettingDefinition;

public class HostsSettingDefinition extends AbstractProviderSettingDefinition<HostnameType, String> {
	
	public HostsSettingDefinition() {
		super("host",
			new HostnameType(), null, "Kodi Hostname", 
			"Hostname or IP address of Kodi.",
			true,
			true);
	}

}
