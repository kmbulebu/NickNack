package com.github.kmbulebu.nicknack.core.attributes.filters;

import java.util.UUID;

/**
 * A clause of an Event or State filter.
 * 
 *
 */
public interface AttributeFilterExpression {
	
	public UUID getAttributeDefinitionUuid();
	
	public Operator getOperator();
	
	public String getOperand();	
	
}