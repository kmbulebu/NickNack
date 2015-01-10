package com.github.kmbulebu.nicknack.providers.dsc.settings;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.github.kmbulebu.nicknack.core.providers.settings.AbstractProviderIntegerSettingDefinition;

public class ActivePartitionsSettingDefinition extends AbstractProviderIntegerSettingDefinition {

	public ActivePartitionsSettingDefinition() {
		super("partitions", "Active Partitions", "Add a partition to NickNack. If none are specified, all partitions are used.", false, true);
	}

	@Override
	public boolean isValid(Integer value) {
		return (value <= 16 && value >= 1);
	}

	@Override
	public List<Integer> getValueChoices() {
		final List<Integer> values = new ArrayList<Integer>(16);
		for (int i = 1; i <= 16; i++) {
			values.add(i);
		}
		return Collections.unmodifiableList(values);
	}

}
