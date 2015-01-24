package com.github.kmbulebu.nicknack.core.attributes.filters;

import java.io.Serializable;
import java.util.UUID;

/**
 * User created, single clause of an Event or State filter.
 * 
 * Specifies the attribute to filter, an operator, and an operand value.
 * 
 */
public interface AttributeFilterExpression<U extends Serializable> {
	
	public UUID getAttributeDefinitionUuid();
	
	public Operator getOperator();
	
	public U[] getOperand();	
	
}