package com.github.kmbulebu.nicknack.server.services;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.github.kmbulebu.nicknack.server.services.exceptions.ProviderNotFoundException;

public interface ProviderSettingsService {
	
	public Map<String, List<?>> getProviderSettings(UUID providerUuid) throws ProviderNotFoundException;
	
	public void setProviderSettings(UUID providerUuid, Map<String, List<?>> settings, boolean disabled) throws ProviderNotFoundException;
	
	public boolean isProviderSettingsComplete(UUID providerUuid) throws ProviderNotFoundException;
	
	public Map<String, List<String>> getProviderSettingErrors(UUID providerUuid) throws ProviderNotFoundException;

	public boolean isProviderEnabled(UUID providerUuid) throws ProviderNotFoundException;

}
