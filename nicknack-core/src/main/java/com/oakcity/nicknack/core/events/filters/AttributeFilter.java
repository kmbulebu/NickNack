package com.oakcity.nicknack.core.events.filters;

import java.util.UUID;

import com.oakcity.nicknack.core.events.filters.operators.Operator;

public interface AttributeFilter {
	
	public UUID getAppliesToAttributeDefinition();
	
	public Operator getOperator();
	
	public String getOperand();
	
}