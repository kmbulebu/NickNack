package com.github.kmbulebu.nicknack.core.events.filters;

import java.util.UUID;

import com.github.kmbulebu.nicknack.core.events.filters.operators.Operator;

public interface AttributeFilterExpression {
	
	public UUID getAttributeDefinitionUuid();
	
	public Operator getOperator();
	
	public String getOperand();	
	
}