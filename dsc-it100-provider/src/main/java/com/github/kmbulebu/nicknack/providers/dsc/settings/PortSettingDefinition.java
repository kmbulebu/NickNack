package com.github.kmbulebu.nicknack.providers.dsc.settings;

import valuetypes.WholeNumberType;

import com.github.kmbulebu.nicknack.core.providers.settings.AbstractProviderSettingDefinition;

public class PortSettingDefinition extends AbstractProviderSettingDefinition<WholeNumberType, Integer> {

	public PortSettingDefinition() {
		super("port", 
			  new WholeNumberType(1, 65536, 1), null, "Port Number", 
			  "Port number of machine with IT-100", true, false);
	}
	
}
