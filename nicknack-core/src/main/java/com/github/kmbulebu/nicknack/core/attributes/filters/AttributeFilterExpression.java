package com.github.kmbulebu.nicknack.core.attributes.filters;

import java.util.UUID;

/**
 * User created, single clause of an Event or State filter.
 * 
 * Specifies the attribute to filter, an operator, and an operand value.
 * 
 */
public interface AttributeFilterExpression {
	
	public UUID getAttributeDefinitionUuid();
	
	public Operator getOperator();
	
	public String getOperand();	
	
}