package com.github.kmbulebu.nicknack.server.model;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Embeddable;

import org.hibernate.annotations.Type;

import com.github.kmbulebu.nicknack.core.attributes.filters.AttributeFilterExpression;
import com.github.kmbulebu.nicknack.core.attributes.filters.Operator;

@Embeddable
public class AttributeFilterExpressionImpl<U extends Serializable> implements AttributeFilterExpression<U> {
	
	private UUID attributeDefinitionUuid;
	
	private Operator operator;
	
	@Type(type="org.hibernate.type.BinaryType")
	private U[] operand;

	@Override
	public UUID getAttributeDefinitionUuid() {
		return attributeDefinitionUuid;
	}

	@Override
	public Operator getOperator() {
		return operator;
	}

	@Override
	public Object[] getOperand() {
		return operand;
	}

}
