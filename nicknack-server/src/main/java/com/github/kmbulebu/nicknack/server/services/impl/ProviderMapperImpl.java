package com.github.kmbulebu.nicknack.server.services.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.github.kmbulebu.nicknack.server.dbmodel.AttributeEntity;
import com.github.kmbulebu.nicknack.server.dbmodel.ProviderEntity;
import com.github.kmbulebu.nicknack.server.dbmodel.ProviderRepository;
import com.github.kmbulebu.nicknack.server.restmodel.Provider;
import com.github.kmbulebu.nicknack.server.services.ProviderMapper;

@Service
public class ProviderMapperImpl implements ProviderMapper {
	
	@Inject
	private AttributeMapper attributeMapper;
	
	@Inject
	private ProviderRepository providerRepository;
	
	/* (non-Javadoc)
	 * @see com.github.kmbulebu.nicknack.server.services.impl.ProviderMapper#map(com.github.kmbulebu.nicknack.core.providers.Provider, com.github.kmbulebu.nicknack.server.dbmodel.ProviderEntity)
	 */
	@Override
	public Provider map(com.github.kmbulebu.nicknack.core.providers.Provider provider, ProviderEntity dbProviderEntity) {
		final Provider restProvider = new Provider();
		
		restProvider.setUuid(provider.getUuid());
		restProvider.setName(provider.getName());
		restProvider.setAuthor(provider.getAuthor());
		restProvider.setVersion(provider.getVersion());
		
		if (dbProviderEntity != null && dbProviderEntity.getSettings() != null) {
			final List<com.github.kmbulebu.nicknack.core.attributes.AttributeDefinition<?, ?>> settingDefinitions = provider.getSettingDefinitions();
			if (settingDefinitions != null) {
				for (com.github.kmbulebu.nicknack.core.attributes.AttributeDefinition<?, ?> settingDefinition : settingDefinitions) {
					// Find a value, if one exists.
					final AttributeEntity attributeEntity = providerRepository.findSetting(provider.getUuid().toString(), settingDefinition.getUUID().toString());
					restProvider.getSettings().add(attributeMapper.map(settingDefinition, provider.getUuid(), attributeEntity));
				}
			}

		}
		
		return restProvider;
	}

}
