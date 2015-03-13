package com.github.kmbulebu.nicknack.core.providers.settings;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractSettingType<T extends Serializable> implements SettingType<T> {
	
	@Override
	public List<String> save(List<T> settingValues) {
		final List<String> stringList = new ArrayList<>(settingValues.size());
		for (int i = 0 ; i < settingValues.size() ; i++) {
			stringList.add(i, save(settingValues.get(i)));
		}
		return stringList;
	}

	@Override
	public List<T> load(List<String> savedData) {
		final List<T> valueList = new ArrayList<T>(savedData.size());
		for (int i = 0; i < savedData.size(); i++) {
			valueList.add(i, load(savedData.get(i)));
		}
		return valueList;
	}

}
