package com.github.kmbulebu.nicknack.providers.dsc.settings;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.github.kmbulebu.nicknack.core.providers.settings.AbstractProviderIntegerSettingDefinition;
import com.github.kmbulebu.nicknack.core.providers.settings.ProviderMultiValueSettingDefinition;

public class ActiveZonesSettingDefinition extends AbstractProviderIntegerSettingDefinition implements ProviderMultiValueSettingDefinition<Integer> {

	public ActiveZonesSettingDefinition() {
		super("zones", "Active Zones", "Add a zone to NickNack. If none are specified, all zones are used.", false);
	}

	@Override
	public boolean isValid(Integer value) {
		return (value <= 128 && value >= 1);
	}

	@Override
	public List<Integer> getValueChoices() {
		final List<Integer> values = new ArrayList<Integer>(128);
		for (int i = 1; i <= 128; i++) {
			values.add(i);
		}
		return Collections.unmodifiableList(values);
	}

}
