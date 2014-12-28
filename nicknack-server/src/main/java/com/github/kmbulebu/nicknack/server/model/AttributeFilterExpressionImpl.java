package com.github.kmbulebu.nicknack.server.model;

import java.util.UUID;

import javax.persistence.Embeddable;

import com.github.kmbulebu.nicknack.core.attributes.filters.AttributeFilterExpression;
import com.github.kmbulebu.nicknack.core.attributes.filters.Operator;

@Embeddable
public class AttributeFilterExpressionImpl implements AttributeFilterExpression {
	
	private UUID attributeDefinitionUuid;
	
	private Operator operator;
	
	private String operand;

	@Override
	public UUID getAttributeDefinitionUuid() {
		return attributeDefinitionUuid;
	}

	@Override
	public Operator getOperator() {
		return operator;
	}

	@Override
	public String getOperand() {
		return operand;
	}

}
