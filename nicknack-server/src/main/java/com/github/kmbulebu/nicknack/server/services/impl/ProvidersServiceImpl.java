package com.github.kmbulebu.nicknack.server.services.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.kmbulebu.nicknack.core.attributes.AttributeValueParser;
import com.github.kmbulebu.nicknack.core.attributes.AttributeValueParser.InvalidValueException;
import com.github.kmbulebu.nicknack.core.valuetypes.ValueType.ParseException;
import com.github.kmbulebu.nicknack.server.Application;
import com.github.kmbulebu.nicknack.server.dbmodel.AttributeEntity;
import com.github.kmbulebu.nicknack.server.dbmodel.AttributeRepository;
import com.github.kmbulebu.nicknack.server.dbmodel.ProviderEntity;
import com.github.kmbulebu.nicknack.server.dbmodel.ProviderRepository;
import com.github.kmbulebu.nicknack.server.exceptions.EntityDoesNotExist;
import com.github.kmbulebu.nicknack.server.restmodel.Attribute;
import com.github.kmbulebu.nicknack.server.restmodel.Provider;
import com.github.kmbulebu.nicknack.server.services.CoreProviderServiceWrapper;
import com.github.kmbulebu.nicknack.server.services.ProviderMapper;
import com.github.kmbulebu.nicknack.server.services.ProvidersService;

@Service
public class ProvidersServiceImpl implements ProvidersService {
	
	private static final Logger LOG = LogManager.getLogger(Application.APP_LOGGER_NAME);
	
	@Inject
	private CoreProviderServiceWrapper coreProviderService;
	
	@Inject
	private ProviderRepository providerRepository;
	
	@Inject
	private AttributeRepository attributeRepository;
	
	@Inject
	private ProviderMapper providerMapper;
	
	private AttributeValueParser valueParser = new AttributeValueParser();
	
	/* (non-Javadoc)
	 * @see com.github.kmbulebu.nicknack.server.services.impl.ProvidersService#getAllProviders()
	 */
	@Override
	public List<Provider> getAllProviders() {
		if (LOG.isTraceEnabled()) {
			LOG.entry();
		}
		
		final List<Provider> restProviders = new LinkedList<>();
		
		for (UUID providerUuid : coreProviderService.getNickNackProviderService().getProviders().keySet()) {
			restProviders.add(getProvider(providerUuid));
		}
		
		if (LOG.isTraceEnabled()) {
			LOG.exit(restProviders);
		}
		return restProviders;
	}
	
	@Override
	public Provider getProvider(UUID uuid) {
		if (LOG.isTraceEnabled()) {
			LOG.entry(uuid);
		}
		
		final com.github.kmbulebu.nicknack.core.providers.Provider coreProvider = coreProviderService.getNickNackProviderService().getProviders().get(uuid);
		
		Provider restProvider = null;
		
		if (coreProvider == null) {
			// TODO Throw exception
		} else {
			final ProviderEntity providerEntity = providerRepository.findByUuid(uuid);
			restProvider = providerMapper.map(coreProvider, providerEntity);
		}
		
		if (LOG.isTraceEnabled()) {
			LOG.exit(restProvider);
		}
		return restProvider;
	}

	@Override
	@Transactional
	public void updateSettings(Provider provider) throws ParseException, InvalidValueException, EntityDoesNotExist {
		if (LOG.isTraceEnabled()) {
			LOG.entry(provider);
		}
		
		final ProviderEntity providerEntity = providerRepository.findByUuid(provider.getUuid());
		if (providerEntity == null) {
			throw new EntityDoesNotExist(ProviderEntity.class.getSimpleName());
		}
		
		
		for (Attribute setting : provider.getSettings()) {
			// If a node doesn't exist yet, create it.
			AttributeEntity attributeEntity = providerRepository.findSetting(provider.getUuid(), setting.getUuid());
			if (attributeEntity == null) {
				attributeEntity = new AttributeEntity();
				attributeEntity.setAttributeDefinitionUuid(setting.getUuid());
				attributeEntity.setMultiValue(setting.isMultiValue());
			}
		
			// Find the attribute definition
			final com.github.kmbulebu.nicknack.core.attributes.AttributeDefinition<?,?> attributeDefinition = coreProviderService.getNickNackProviderService().getAttributeDefinition(setting.getUuid());
			
			// If doesn't exist, ignore.
			if (attributeDefinition != null) {
				// Convert to object and back to String to validate.
				final Object[] typedValues = valueParser.toObjects(attributeDefinition, setting.getValues());
				final String[] unTypedValues = valueParser.toStrings(attributeDefinition, typedValues);
				if (attributeDefinition.isMultiValue()) {
					attributeEntity.setValues(unTypedValues);
				} else if (unTypedValues.length > 0) {
					attributeEntity.setValues(new String[]{unTypedValues[0]});
				} else {
					attributeEntity.setValues(new String[]{});
				}
				attributeRepository.save(attributeEntity);
				providerEntity.getSettings().add(attributeEntity);
			}
		}
		
		providerRepository.save(providerEntity);
		
		if (LOG.isTraceEnabled()) {
			LOG.exit();
		}
	}

	
	
}
