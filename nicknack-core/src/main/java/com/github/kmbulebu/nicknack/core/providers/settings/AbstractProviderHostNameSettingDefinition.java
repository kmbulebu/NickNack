package com.github.kmbulebu.nicknack.core.providers.settings;


public abstract class AbstractProviderHostNameSettingDefinition extends AbstractProviderStringSettingDefinition {
	
	private static final String HOST_IP_REGEX = "^((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)(\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)){3})$|^((([a-zA-Z0-9]|[a-zA-Z0-9][a-zA-Z0-9\\-]*[a-zA-Z0-9])\\.)*([A-Za-z0-9]|[A-Za-z0-9][A-Za-z0-9\\-]*[A-Za-z0-9]))$";

	public AbstractProviderHostNameSettingDefinition(String key, String name, String description, boolean isRequired) {
		super(key, name, description, isRequired);
	}

	@Override
	public boolean isValid(String value) {
		return value.matches(HOST_IP_REGEX);
	}

}
