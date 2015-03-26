package com.github.kmbulebu.nicknack.server.services.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.github.kmbulebu.nicknack.core.attributes.AttributeValueParser;
import com.github.kmbulebu.nicknack.core.valuetypes.ValueType;
import com.github.kmbulebu.nicknack.server.Application;
import com.github.kmbulebu.nicknack.server.restmodel.AttributeDefinition;
import com.github.kmbulebu.nicknack.server.restmodel.EventDefinition;
import com.github.kmbulebu.nicknack.server.services.CoreProviderServiceWrapper;
import com.github.kmbulebu.nicknack.server.services.EventDefinitionsService;

@Service
public class EventDefinitionsServiceImpl implements EventDefinitionsService {
	
	private static final Logger LOG = LogManager.getLogger(Application.APP_LOGGER_NAME);
	
	@Inject
	private CoreProviderServiceWrapper coreProviderService;
	
	private AttributeValueParser valueParser = new AttributeValueParser();

	@Override
	public List<EventDefinition> getAllEventDefinitions() {
		if (LOG.isTraceEnabled()) {
			LOG.entry();
		}
		
		final Collection<com.github.kmbulebu.nicknack.core.events.EventDefinition> coreEventDefinitions = coreProviderService.getNickNackProviderService().getEventDefinitions();
		
		final List<EventDefinition> eventDefinitions = new ArrayList<>(coreEventDefinitions.size());
		
		for (com.github.kmbulebu.nicknack.core.events.EventDefinition coreEventDefinition : coreEventDefinitions) {
			eventDefinitions.add(mapEventDefinition(coreEventDefinition));
		}
		
		if (LOG.isTraceEnabled()) {
			LOG.exit(eventDefinitions);
		}
		return eventDefinitions;
	}

	@Override
	public List<EventDefinition> getEventDefinitionsByProvider(UUID providerUuid) {
		if (LOG.isTraceEnabled()) {
			LOG.entry();
		}
		
		final Collection<com.github.kmbulebu.nicknack.core.events.EventDefinition> coreEventDefinitions = coreProviderService.getNickNackProviderService().getEventDefinitionsByProviderUuid(providerUuid);
		
		final List<EventDefinition> eventDefinitions = new ArrayList<>(coreEventDefinitions.size());
		
		for (com.github.kmbulebu.nicknack.core.events.EventDefinition coreEventDefinition : coreEventDefinitions) {
			eventDefinitions.add(mapEventDefinition(coreEventDefinition));
		}
		
		if (LOG.isTraceEnabled()) {
			LOG.exit(eventDefinitions);
		}
		return eventDefinitions;
	}

	@Override
	public EventDefinition getEventDefinition(UUID uuid) {
		if (LOG.isTraceEnabled()) {
			LOG.entry();
		}
		
		final com.github.kmbulebu.nicknack.core.events.EventDefinition coreEventDefinition = coreProviderService.getNickNackProviderService().getEventDefinition(uuid);
		
		EventDefinition eventDefinition = null;
		
		if (coreEventDefinition != null) {
			eventDefinition = mapEventDefinition(coreEventDefinition);
		}
		
		if (LOG.isTraceEnabled()) {
			LOG.exit(eventDefinition);
		}
		return eventDefinition;
	}

	private EventDefinition mapEventDefinition(com.github.kmbulebu.nicknack.core.events.EventDefinition coreEventDefinition) {
		
		final EventDefinition eventDefinition = new EventDefinition();
		
		eventDefinition.setName(coreEventDefinition.getName());
		eventDefinition.setUuid(coreEventDefinition.getUUID());
		
		final List<AttributeDefinition> attributes = new ArrayList<>(coreEventDefinition.getAttributeDefinitions().size());
		
		for (com.github.kmbulebu.nicknack.core.attributes.AttributeDefinition<?,?> coreAttributeDefinition : coreEventDefinition.getAttributeDefinitions()) {
			attributes.add(mapAttribute(coreAttributeDefinition));
		}
		
		eventDefinition.setAttributes(attributes);
		return eventDefinition;
	}
	
	@SuppressWarnings("unchecked")
	private AttributeDefinition mapAttribute(com.github.kmbulebu.nicknack.core.attributes.AttributeDefinition<?, ?> attributeDefinition) {
		AttributeDefinition setting = new AttributeDefinition();
		setting.setUuid(attributeDefinition.getUUID());
		setting.setName(attributeDefinition.getName());
		setting.setRequired(attributeDefinition.isRequired());
		setting.setDescription(attributeDefinition.getDescription());
		if (attributeDefinition.getValueChoices() != null) {
			attributeDefinition.getValueChoices().getValueChoices();
			setting.setChoices(valueParser.toStringsFromList(attributeDefinition, (List<Object>) attributeDefinition.getValueChoices().getValueChoices()));
		}
		setting.setValueType(mapValueType(attributeDefinition.getValueType()));
		return setting;
		
	}
	
	private AttributeDefinition.ValueType mapValueType(ValueType<?> attributeValueType) {
		AttributeDefinition.ValueType valueType = new AttributeDefinition.ValueType();
		valueType.setName(attributeValueType.getName());
		valueType.setIsValidRegex(attributeValueType.getIsValidRegEx());
		return valueType;
	}

}
