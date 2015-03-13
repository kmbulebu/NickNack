package com.github.kmbulebu.nicknack.server.services.impl;

import java.util.List;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.kmbulebu.nicknack.core.providers.Provider;
import com.github.kmbulebu.nicknack.core.providers.ProviderService;
import com.github.kmbulebu.nicknack.core.providers.settings.SettingDefinition;
import com.github.kmbulebu.nicknack.server.Application;
import com.github.kmbulebu.nicknack.server.services.ProviderSettingDefinitionsService;
import com.github.kmbulebu.nicknack.server.services.exceptions.ProviderNotFoundException;

@Service
public class ProviderSettingDefinitionsServiceImpl implements ProviderSettingDefinitionsService {
	
	private static final Logger LOG = LogManager.getLogger(Application.APP_LOGGER_NAME);
	
	@Autowired
	private ProviderService providerService;

	@Override
	public List<? extends SettingDefinition<?>> getSettingDefinitions(UUID providerUuid) throws ProviderNotFoundException {
		if (LOG.isTraceEnabled()) {
			LOG.entry(providerUuid);
		}
		
		final Provider provider = providerService.getProviders().get(providerUuid);
		
		if (provider == null) {
			throw new ProviderNotFoundException(providerUuid);
		}
		
		final List<? extends SettingDefinition<?>> settingDefinitions = provider.getSettingDefinitions();
		
		
		if (LOG.isTraceEnabled()) {
			LOG.exit(settingDefinitions);
		}
		return settingDefinitions;
	}

}
