package com.github.kmbulebu.nicknack.server.services.impl;

import java.util.List;

import com.github.kmbulebu.nicknack.core.attributes.AttributeValueParser;
import com.github.kmbulebu.nicknack.core.valuetypes.ValueType;
import com.github.kmbulebu.nicknack.server.restmodel.AttributeDefinition;

public class AttributeDefinitionMapper {

	private AttributeValueParser valueParser = new AttributeValueParser();
	
	public AttributeDefinition map(com.github.kmbulebu.nicknack.core.attributes.AttributeDefinition<?, ?> attributeDefinition) {
		AttributeDefinition setting = new AttributeDefinition();
		mapAttribute(setting, attributeDefinition);
		return setting;
		
	}
	
	@SuppressWarnings("unchecked")
	protected void mapAttribute(AttributeDefinition attribute, com.github.kmbulebu.nicknack.core.attributes.AttributeDefinition<?, ?> attributeDefinition) {
		attribute.setUuid(attributeDefinition.getUUID());
		attribute.setName(attributeDefinition.getName());
		attribute.setRequired(attributeDefinition.isRequired());
		attribute.setDescription(attributeDefinition.getDescription());
		if (attributeDefinition.getValueChoices() != null) {
			attributeDefinition.getValueChoices().getValueChoices();
			attribute.setChoices(valueParser.toStringsFromList(attributeDefinition, (List<Object>) attributeDefinition.getValueChoices().getValueChoices()));
		}
		attribute.setValueType(mapValueType(attributeDefinition.getValueType()));
	}
	
	private AttributeDefinition.ValueType mapValueType(ValueType<?> attributeValueType) {
		AttributeDefinition.ValueType valueType = new AttributeDefinition.ValueType();
		valueType.setName(attributeValueType.getName());
		valueType.setIsValidRegex(attributeValueType.getIsValidRegEx());
		
		final AttributeDefinition.Operator[] operators = new AttributeDefinition.Operator[attributeValueType.getSupportedOperators().length];
		for (int i = 0; i < attributeValueType.getSupportedOperators().length; i++) {
			operators[i] = mapOperator(attributeValueType.getSupportedOperators()[i]);
		}
		valueType.setSupportedOperators(operators);
		return valueType;
	}
	
	private AttributeDefinition.Operator mapOperator(com.github.kmbulebu.nicknack.core.attributes.filters.Operator coreOperator) {
		final AttributeDefinition.Operator operator = new AttributeDefinition.Operator();
		operator.setName(coreOperator.getName());
		operator.setDescription(coreOperator.getDescription());
		operator.setArrayOperand(coreOperator.isArrayOperand());
		
		return operator;
	}

}
