package com.github.kmbulebu.nicknack.server.services.impl;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.github.kmbulebu.nicknack.server.dbmodel.AttributeEntity;
import com.github.kmbulebu.nicknack.server.restmodel.Attribute;

@Service
public class AttributeMapper extends AttributeDefinitionMapper {
	
	public Attribute map(
			com.github.kmbulebu.nicknack.core.attributes.AttributeDefinition<?, ?> attributeDefinition, UUID providerUuid, AttributeEntity attributeEntity) {
		final Attribute attribute = new Attribute();
		super.mapAttribute(attribute, attributeDefinition, providerUuid);
		
		if (attributeEntity != null) {
			attribute.setValues(attributeEntity.getValues());
		}
		return attribute;
	}

}
