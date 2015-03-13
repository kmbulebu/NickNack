package com.github.kmbulebu.nicknack.providers.dsc.settings;

import valuetypes.WholeNumberType;

import com.github.kmbulebu.nicknack.core.providers.settings.AbstractProviderSettingDefinition;

public class ActiveZonesSettingDefinition extends AbstractProviderSettingDefinition<WholeNumberType, Integer> {

	public ActiveZonesSettingDefinition() {
		super("zones", new WholeNumberType(1, 128, 1), null, "Active Zones", "Add a zone to NickNack. If none are specified, all zones are used.", false, true);
	}

}
