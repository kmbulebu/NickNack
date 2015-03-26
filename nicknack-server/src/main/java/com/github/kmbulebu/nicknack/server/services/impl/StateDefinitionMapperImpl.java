package com.github.kmbulebu.nicknack.server.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.github.kmbulebu.nicknack.server.restmodel.AttributeDefinition;
import com.github.kmbulebu.nicknack.server.restmodel.StateDefinition;
import com.github.kmbulebu.nicknack.server.services.StateDefinitionMapper;

@Service
public class StateDefinitionMapperImpl implements StateDefinitionMapper {
	
	private AttributeDefinitionMapper attributeMapper = new AttributeDefinitionMapper();
	
	/* (non-Javadoc)
	 * @see com.github.kmbulebu.nicknack.server.services.impl.StateDefinitionMapper#map(com.github.kmbulebu.nicknack.core.states.StateDefinition)
	 */
	@Override
	public StateDefinition map(com.github.kmbulebu.nicknack.core.states.StateDefinition coreStateDefinition) {
		
		final StateDefinition stateDefinition = new StateDefinition();
		
		stateDefinition.setName(coreStateDefinition.getName());
		stateDefinition.setUuid(coreStateDefinition.getUUID());
		
		final List<AttributeDefinition> attributes = new ArrayList<>(coreStateDefinition.getAttributeDefinitions().size());
		
		for (com.github.kmbulebu.nicknack.core.attributes.AttributeDefinition<?,?> coreAttributeDefinition : coreStateDefinition.getAttributeDefinitions()) {
			attributes.add(attributeMapper.map(coreAttributeDefinition));
		}
		
		stateDefinition.setAttributes(attributes);
		return stateDefinition;
	}	

}
