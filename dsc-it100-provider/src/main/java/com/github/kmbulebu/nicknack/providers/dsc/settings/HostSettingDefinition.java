package com.github.kmbulebu.nicknack.providers.dsc.settings;

import com.github.kmbulebu.nicknack.core.providers.settings.AbstractProviderSettingDefinition;
import com.github.kmbulebu.nicknack.core.valuetypes.HostnameType;

public class HostSettingDefinition extends AbstractProviderSettingDefinition<HostnameType> {
	
	public HostSettingDefinition() {
		super("host",
			new HostnameType(),
			null,
			"Hostname", 
			"Hostname of system with IT-100",
			true, false);
	}


}
