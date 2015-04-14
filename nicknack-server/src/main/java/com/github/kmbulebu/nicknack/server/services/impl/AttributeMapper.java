package com.github.kmbulebu.nicknack.server.services.impl;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.github.kmbulebu.nicknack.core.attributes.AttributeValueParser;
import com.github.kmbulebu.nicknack.server.dbmodel.AttributeEntity;
import com.github.kmbulebu.nicknack.server.restmodel.Attribute;
import com.github.kmbulebu.nicknack.server.restmodel.impl.AttributeDefinitionImpl;
import com.github.kmbulebu.nicknack.server.restmodel.impl.AttributeImpl;

@Service
public class AttributeMapper extends AttributeDefinitionMapper {
	
	private AttributeValueParser valueParser = new AttributeValueParser();
	
	public Attribute map(
			com.github.kmbulebu.nicknack.core.attributes.AttributeDefinition<?, ?> attributeDefinition, UUID providerUuid, AttributeEntity attributeEntity) {
		final AttributeDefinitionImpl attributeDefinitionImpl = new AttributeDefinitionImpl();
		super.mapAttribute(attributeDefinitionImpl, attributeDefinition, providerUuid);
		
		final AttributeImpl attribute = new AttributeImpl(attributeDefinitionImpl);
		
		if (attributeEntity != null) {
			attribute.setValues(attributeEntity.getValues());
		}
		return attribute;
	}
	
	public Attribute mapFromSingleValue(
			com.github.kmbulebu.nicknack.core.attributes.AttributeDefinition<?, ?> attributeDefinition, UUID providerUuid, Object singleValue) {
		final AttributeDefinitionImpl attributeDefinitionImpl = new AttributeDefinitionImpl();
		super.mapAttribute(attributeDefinitionImpl, attributeDefinition, providerUuid);
		
		final AttributeImpl attribute = new AttributeImpl(attributeDefinitionImpl);
		
		// TODO Can a state have multiple values for a single attribute? 
		attribute.setValue(valueParser.toString(attributeDefinition, singleValue));

		return attribute;
	}

}
