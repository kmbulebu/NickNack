package com.github.kmbulebu.nicknack.server.services.impl;

import com.github.kmbulebu.nicknack.server.dbmodel.AttributeEntity;
import com.github.kmbulebu.nicknack.server.restmodel.Attribute;

public class AttributeMapper extends AttributeDefinitionMapper {
	
	public Attribute map(
			com.github.kmbulebu.nicknack.core.attributes.AttributeDefinition<?, ?> attributeDefinition, AttributeEntity attributeEntity) {
		final Attribute attribute = new Attribute();
		super.mapAttribute(attribute, attributeDefinition);
		
		if (attributeEntity != null) {
			attribute.setValues(attributeEntity.getValues());
		}
		return attribute;
	}

}
