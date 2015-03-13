package com.github.kmbulebu.nicknack.server.services.impl;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.kmbulebu.nicknack.core.providers.Provider;
import com.github.kmbulebu.nicknack.core.providers.ProviderService;
import com.github.kmbulebu.nicknack.server.Application;
import com.github.kmbulebu.nicknack.server.services.ProviderSettingsService;
import com.github.kmbulebu.nicknack.server.services.exceptions.ProviderNotFoundException;

@Service
public class ProviderSettingsServiceImpl implements ProviderSettingsService {
	
	private static final Logger LOG = LogManager.getLogger(Application.APP_LOGGER_NAME);
	
	@Autowired
	private ProviderService providerService;

	@Override
	public Map<String, List<String>> getProviderSettings(UUID providerUuid) throws ProviderNotFoundException {
		if (LOG.isTraceEnabled()) {
			LOG.entry(providerUuid);
		}
		
		final Provider provider = providerService.getProviders().get(providerUuid);
		
		if (provider == null) {
			throw new ProviderNotFoundException(providerUuid);
		}
		
		final Map<String, List<String>> settings = providerService.getProviderSettings(providerUuid);
		
		if (LOG.isTraceEnabled()) {
			LOG.exit(providerUuid);
		}
		return settings;
	}

	@Override
	public boolean isProviderSettingsComplete(UUID providerUuid) throws ProviderNotFoundException {
		if (LOG.isTraceEnabled()) {
			LOG.entry(providerUuid);
		}
		
		final Provider provider = providerService.getProviders().get(providerUuid);
		
		if (provider == null) {
			throw new ProviderNotFoundException(providerUuid);
		}
		
		final boolean complete = providerService.isProviderSettingsComplete(providerUuid);
		
		if (LOG.isTraceEnabled()) {
			LOG.exit(complete);
		}
		return complete;
	}
	
	@Override
	public boolean isProviderEnabled(UUID providerUuid) throws ProviderNotFoundException {
		if (LOG.isTraceEnabled()) {
			LOG.entry(providerUuid);
		}
		
		final Provider provider = providerService.getProviders().get(providerUuid);
		
		if (provider == null) {
			throw new ProviderNotFoundException(providerUuid);
		}
		
		final boolean enabled = providerService.isProviderEnabled(providerUuid);
		
		if (LOG.isTraceEnabled()) {
			LOG.exit(enabled);
		}
		return enabled;
	}

	@Override
	public Map<String, List<String>> getProviderSettingErrors(UUID providerUuid) throws ProviderNotFoundException {
		if (LOG.isTraceEnabled()) {
			LOG.entry(providerUuid);
		}
		
		final Provider provider = providerService.getProviders().get(providerUuid);
		
		if (provider == null) {
			throw new ProviderNotFoundException(providerUuid);
		}
		
		final Map<String, List<String>> errors = providerService.getProviderSettingsErrors(providerUuid);
		
		if (LOG.isTraceEnabled()) {
			LOG.exit(errors);
		}
		return errors;
	}

	@Override
	public void setProviderSettings(UUID providerUuid, Map<String, List<String>> settings, boolean disabled) throws ProviderNotFoundException {
		if (LOG.isTraceEnabled()) {
			LOG.entry(providerUuid, settings);
		}
		
		final Provider provider = providerService.getProviders().get(providerUuid);
		
		if (provider == null) {
			throw new ProviderNotFoundException(providerUuid);
		}
		
		providerService.setProviderSettings(providerUuid, settings, disabled);
		
		if (LOG.isTraceEnabled()) {
			LOG.exit();
		}
	}
	
	

}
