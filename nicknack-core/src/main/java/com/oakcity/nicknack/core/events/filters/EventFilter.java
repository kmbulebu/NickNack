package com.oakcity.nicknack.core.events.filters;

import java.util.List;
import java.util.UUID;

import com.oakcity.nicknack.core.events.filters.operators.Operator;


public interface EventFilter {
	
	public UUID getAppliesToEventDefinition();
	
	public String getDescription();
	
	public List<AttributeFilter> getAttributeFilters();
	
	public interface AttributeFilter {
		
		public UUID getAppliesToAttributeDefinition();
		
		public Operator getOperator();
		
		public String getOperand();
		
	}

}
