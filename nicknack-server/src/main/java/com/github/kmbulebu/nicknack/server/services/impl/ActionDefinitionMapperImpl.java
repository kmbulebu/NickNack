package com.github.kmbulebu.nicknack.server.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.github.kmbulebu.nicknack.server.restmodel.AttributeDefinition;
import com.github.kmbulebu.nicknack.server.restmodel.ActionDefinition;
import com.github.kmbulebu.nicknack.server.services.ActionDefinitionMapper;

@Service
public class ActionDefinitionMapperImpl implements ActionDefinitionMapper {
	
	private AttributeDefinitionMapper attributeMapper = new AttributeDefinitionMapper();
	
	/* (non-Javadoc)
	 * @see com.github.kmbulebu.nicknack.server.services.impl.ActionDefinitionMapper#map(com.github.kmbulebu.nicknack.core.actions.ActionDefinition)
	 */
	@Override
	public ActionDefinition map(com.github.kmbulebu.nicknack.core.actions.ActionDefinition coreActionDefinition) {
		
		final ActionDefinition actionDefinition = new ActionDefinition();
		
		actionDefinition.setName(coreActionDefinition.getName());
		actionDefinition.setDescription(coreActionDefinition.getDescription());
		actionDefinition.setUuid(coreActionDefinition.getUUID());
		
		final List<AttributeDefinition> attributes = new ArrayList<>(coreActionDefinition.getAttributeDefinitions().size());
		
		for (com.github.kmbulebu.nicknack.core.attributes.AttributeDefinition<?,?> coreAttributeDefinition : coreActionDefinition.getAttributeDefinitions()) {
			attributes.add(attributeMapper.map(coreAttributeDefinition));
		}
		
		actionDefinition.setAttributes(attributes);
		return actionDefinition;
	}	

}
