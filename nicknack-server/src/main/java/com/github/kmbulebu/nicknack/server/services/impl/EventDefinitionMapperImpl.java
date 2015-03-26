package com.github.kmbulebu.nicknack.server.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.github.kmbulebu.nicknack.server.restmodel.AttributeDefinition;
import com.github.kmbulebu.nicknack.server.restmodel.EventDefinition;
import com.github.kmbulebu.nicknack.server.services.EventDefinitionMapper;

@Service
public class EventDefinitionMapperImpl implements EventDefinitionMapper {
	
	private AttributeDefinitionMapper attributeMapper = new AttributeDefinitionMapper();
	
	/* (non-Javadoc)
	 * @see com.github.kmbulebu.nicknack.server.services.impl.EventDefinitionMapper#map(com.github.kmbulebu.nicknack.core.events.EventDefinition)
	 */
	@Override
	public EventDefinition map(com.github.kmbulebu.nicknack.core.events.EventDefinition coreEventDefinition) {
		
		final EventDefinition eventDefinition = new EventDefinition();
		
		eventDefinition.setName(coreEventDefinition.getName());
		eventDefinition.setUuid(coreEventDefinition.getUUID());
		
		final List<AttributeDefinition> attributes = new ArrayList<>(coreEventDefinition.getAttributeDefinitions().size());
		
		for (com.github.kmbulebu.nicknack.core.attributes.AttributeDefinition<?,?> coreAttributeDefinition : coreEventDefinition.getAttributeDefinitions()) {
			attributes.add(attributeMapper.map(coreAttributeDefinition));
		}
		
		eventDefinition.setAttributes(attributes);
		return eventDefinition;
	}	

}