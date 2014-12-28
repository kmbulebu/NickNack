package com.github.kmbulebu.nicknack.core.attributes.filters;

import java.util.UUID;

public interface AttributeFilterExpression {
	
	public UUID getAttributeDefinitionUuid();
	
	public Operator getOperator();
	
	public String getOperand();	
	
}