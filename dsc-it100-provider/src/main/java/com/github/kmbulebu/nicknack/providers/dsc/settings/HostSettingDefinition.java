package com.github.kmbulebu.nicknack.providers.dsc.settings;

import valuetypes.HostnameType;

import com.github.kmbulebu.nicknack.core.providers.settings.AbstractProviderSettingDefinition;

public class HostSettingDefinition extends AbstractProviderSettingDefinition<HostnameType, String> {
	
	public HostSettingDefinition() {
		super("host",
			new HostnameType(),
			null,
			"Hostname", 
			"Hostname of system with IT-100",
			true, false);
	}


}
