package com.github.kmbulebu.nicknack.server.services;

import java.util.List;
import java.util.UUID;

import com.github.kmbulebu.nicknack.core.providers.settings.SettingDefinition;
import com.github.kmbulebu.nicknack.server.services.exceptions.ProviderNotFoundException;

public interface ProviderSettingDefinitionsService {
	
	public List<? extends SettingDefinition<?, ?>> getSettingDefinitions(UUID providerUuid) throws ProviderNotFoundException;

}
