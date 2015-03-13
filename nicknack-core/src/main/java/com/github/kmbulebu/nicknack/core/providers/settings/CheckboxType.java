package com.github.kmbulebu.nicknack.core.providers.settings;


public class CheckboxType extends AbstractSettingType<Boolean> {
	
	@Override
	public Class<Boolean> getTypeClass() {
		return Boolean.class;
	}

	@Override
	public String getName() {
		return "checkbox";
	}
	
	@Override
	public boolean isValid(Boolean input) {
		return true;
	}

	@Override
	public String save(Boolean settingValue) {
		return settingValue.toString();
	}

	@Override
	public Boolean load(String savedData) {
		return Boolean.parseBoolean(savedData);
	}



}
