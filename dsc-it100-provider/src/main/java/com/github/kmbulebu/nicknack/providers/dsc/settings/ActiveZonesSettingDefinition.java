package com.github.kmbulebu.nicknack.providers.dsc.settings;

import com.github.kmbulebu.nicknack.core.providers.settings.AbstractProviderSettingDefinition;
import com.github.kmbulebu.nicknack.core.valuetypes.WholeNumberType;

public class ActiveZonesSettingDefinition extends AbstractProviderSettingDefinition<WholeNumberType> {

	public ActiveZonesSettingDefinition() {
		super("zones", new WholeNumberType(1, 128, 1), null, "Active Zones", "Add a zone to NickNack. If none are specified, all zones are used.", false, true);
	}

}
