package com.github.kmbulebu.nicknack.server.services.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.kmbulebu.nicknack.core.attributes.AttributeDefinition;
import com.github.kmbulebu.nicknack.core.events.EventDefinition;
import com.github.kmbulebu.nicknack.core.providers.Provider;
import com.github.kmbulebu.nicknack.core.providers.ProviderService;
import com.github.kmbulebu.nicknack.server.Application;
import com.github.kmbulebu.nicknack.server.services.EventDefinitionService;


@Service
public class EventDefinitionServiceImpl implements EventDefinitionService {
	
	private static final Logger LOG = LogManager.getLogger(Application.APP_LOGGER_NAME);
	
	@Autowired
	private ProviderService providerService;


	@Override
	public List<EventDefinition> getEventDefinitions() {
		if (LOG.isTraceEnabled()) {
			LOG.entry();
		}
		
		final Collection<EventDefinition> set = providerService.getEventDefinitions().values();
		
		// TODO Add exceptions for not found, etc.
		
		final List<EventDefinition> eventDefinitions = new ArrayList<EventDefinition>(set);
		
		if (LOG.isTraceEnabled()) {
			LOG.exit(eventDefinitions);
		}
		return eventDefinitions;
		
	}

	@Override
	public EventDefinition getEventDefinition(final UUID uuid) {
		if (LOG.isTraceEnabled()) {
			LOG.entry(uuid);
		}
		
		final EventDefinition eventDefinition = providerService.getEventDefinitions().get(uuid);
		
		// TODO Add exceptions for not found, etc.
		
		if (LOG.isTraceEnabled()) {
			LOG.exit(eventDefinition);
		}
		return eventDefinition;
	}
	
	@Override
	public List<AttributeDefinition> getAttributeDefinitions(final UUID eventUUID) {
		if (LOG.isTraceEnabled()) {
			LOG.entry(eventUUID);
		}
		
		final EventDefinition eventDefinition = getEventDefinition(eventUUID);
				
		// TODO Add exceptions for not found, etc.
		
		final List<AttributeDefinition> attributeDefinitions = Collections.unmodifiableList(eventDefinition.getAttributeDefinitions());
		
		if (LOG.isTraceEnabled()) {
			LOG.exit(attributeDefinitions);
		}
		return attributeDefinitions;
	}
	
	@Override
	public AttributeDefinition getAttributeDefinition(final UUID eventUUID, final UUID uuid) {
		if (LOG.isTraceEnabled()) {
			LOG.entry(eventUUID, uuid);
		}
		
		final List<AttributeDefinition> attributeDefinitions = getAttributeDefinitions(eventUUID);
		
		AttributeDefinition attributeDefinition = null;
		
		for (AttributeDefinition anAttributeDefinition : attributeDefinitions) {
			if (uuid.equals(anAttributeDefinition.getUUID())) {
				attributeDefinition = anAttributeDefinition;
				break;
			}
		}
		
		if (LOG.isTraceEnabled()) {
			LOG.exit(attributeDefinition);
		}
		return attributeDefinition;
	}

	@Override
	public Map<String, String> getAttributeDefinitionValues(UUID eventUuid, UUID uuid) {
		if (LOG.isTraceEnabled()) {
			LOG.entry(eventUuid, uuid);
		}
		
		final Provider provider = providerService.getProviderByEventDefinitionUuid(eventUuid);
		
		final Map<String, String> values = provider.getAttributeDefinitionValues(eventUuid, uuid);
		
		if (LOG.isTraceEnabled()) {
			LOG.exit(values);
		}
		return values;
	}

	@Override
	public Collection<EventDefinition> getEventDefinitionsByProvider(UUID providerUuid) {
		if (LOG.isTraceEnabled()) {
			LOG.entry(providerUuid);
		}
		
		final Collection<EventDefinition> eventDefinitions = Collections.unmodifiableCollection(providerService.getProviders().get(providerUuid).getEventDefinitions());
		
		if (LOG.isTraceEnabled()) {
			LOG.exit(eventDefinitions);
		}
		return eventDefinitions;
	}

}
