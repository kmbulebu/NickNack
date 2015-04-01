package com.github.kmbulebu.nicknack.server.services.impl;

import java.lang.reflect.Array;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.github.kmbulebu.nicknack.core.attributes.AttributeCollection;
import com.github.kmbulebu.nicknack.core.attributes.AttributeDefinition;
import com.github.kmbulebu.nicknack.core.attributes.AttributeValueParser;
import com.github.kmbulebu.nicknack.core.attributes.AttributeValueParser.InvalidValueException;
import com.github.kmbulebu.nicknack.core.providers.ProviderService;
import com.github.kmbulebu.nicknack.core.providers.ProviderServiceImpl;
import com.github.kmbulebu.nicknack.core.valuetypes.ValueType.ParseException;
import com.github.kmbulebu.nicknack.server.Application;
import com.github.kmbulebu.nicknack.server.dbmodel.AttributeEntity;
import com.github.kmbulebu.nicknack.server.dbmodel.ProviderEntity;
import com.github.kmbulebu.nicknack.server.dbmodel.ProviderRepository;
import com.github.kmbulebu.nicknack.server.services.CoreProviderServiceWrapper;

@Service
public class CoreProviderServiceWrapperImpl implements CoreProviderServiceWrapper {

	private static final Logger LOG = LogManager.getLogger(Application.APP_LOGGER_NAME);

	@Inject
	private Path providersPath;

	@Inject
	private ProviderRepository providerRepository;

	private ProviderService providerService;

	@Inject
	protected PlatformTransactionManager txManager;
	
	private AttributeValueParser valueParser = new AttributeValueParser();

	@PostConstruct
	@Transactional
	private void initializeProviderService() {
		if (LOG.isTraceEnabled()) {
			LOG.entry();
		}

		TransactionTemplate tmpl = new TransactionTemplate(txManager);
		tmpl.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				providerService = ProviderServiceImpl.getInstance(providersPath);

				Iterator<ProviderEntity> providerEntityIterator = providerRepository.findAll().iterator();

				// Tell the core service about the settings we have.
				while (providerEntityIterator.hasNext()) {
					final ProviderEntity providerEntity = providerEntityIterator.next();

					if (providerEntity.getSettings() != null) {
						final AttributeCollection settings = wrapAttributeValueMap(toAttributeValueMap(providerEntity
								.getSettings()));
						providerService.setProviderSettings(providerEntity.getUuid(), settings);
					}
				}

				if (LOG.isInfoEnabled()) {
					LOG.info("Initializing Providers.");
				}

				providerService.initialize();

				// Get a Set of the providers to find which ones aren't yet in
				// our database.
				final Set<UUID> providerUuids = providerService.getProviders().keySet();

				for (UUID providerUuid : providerUuids) {
					ProviderEntity providerEntity = providerRepository.findByUuid(providerUuid);

					// Create one if it doesn't exist.
					if (providerEntity == null) {
						providerEntity = new ProviderEntity();
						providerEntity.setUuid(providerUuid);
						providerRepository.save(providerEntity);
					}

				}
			}
		});

		if (LOG.isTraceEnabled()) {
			LOG.exit();
		}
	}

	private Map<UUID, ?> toAttributeValueMap(Set<AttributeEntity> attributeEntities) {
		if (LOG.isTraceEnabled()) {
			LOG.entry(attributeEntities);
		}
		final Map<UUID, Object> map = new HashMap<>();

		for (AttributeEntity attributeEntity : attributeEntities) {
			final AttributeDefinition<?,?> attributeDefinition = providerService.getAttributeDefinition(attributeEntity.getAttributeDefinitionUuid());
			
			if (attributeDefinition != null) {
				try {
					final Object[] typedValues = valueParser.toObjects(attributeDefinition, attributeEntity.getValues());
					if (attributeDefinition.isMultiValue()) {
						final Object[] typedValueArray = (Object[]) Array.newInstance(attributeDefinition.getValueType().getValueClass(), typedValues.length);
						for (int i = 0; i < typedValues.length; i++) {
							typedValueArray[i] = typedValues[i];
						}
						map.put(attributeEntity.getAttributeDefinitionUuid(), typedValueArray);
					} else if (typedValues != null && typedValues.length > 0) {
						map.put(attributeEntity.getAttributeDefinitionUuid(), typedValues[0]);
					}
				} catch (ParseException | InvalidValueException e) {
					LOG.warn("Could not load provider setting from database: " + attributeEntity, e);
				}
				
			}
		}

		if (LOG.isTraceEnabled()) {
			LOG.exit(map);
		}
		return map;
	}

	private AttributeCollection wrapAttributeValueMap(final Map<UUID, ?> attributeValueMap) {
		return new AttributeCollection() {

			@Override
			public Map<UUID, ?> getAttributes() {
				return attributeValueMap;
			}
		};
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.github.kmbulebu.nicknack.server.services.impl.ProviderServiceWrapper
	 * #getNickNackProviderService()
	 */
	@Override
	public ProviderService getNickNackProviderService() {
		return providerService;
	}

}
