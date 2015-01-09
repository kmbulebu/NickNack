package com.github.kmbulebu.nicknack.providers.dsc.settings;

import java.util.List;

import com.github.kmbulebu.nicknack.core.providers.settings.AbstractProviderIntegerSettingDefinition;

public class PortSettingDefinition extends AbstractProviderIntegerSettingDefinition {

	public PortSettingDefinition() {
		super("port", "Port Number", "Port number of machine with IT-100", true);
	}

	@Override
	public boolean isValid(Integer value) {
		return (value <= 65536 && value > 0);
	}

	@Override
	public List<Integer> getValueChoices() {
		return null;
	}

}
