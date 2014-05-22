package com.oakcity.nicknack.events.filters;

import java.util.List;

import com.oakcity.nicknack.events.Event.AttributeDefinition;
import com.oakcity.nicknack.events.Event.EventDefinition;
import com.oakcity.nicknack.events.filters.operators.Operator;


public interface EventFilter {
	
	public EventDefinition getAppliesTo();
	
	public String getDescription();
	
	public List<AttributeFilter> getAttributeFilters();
	
	public interface AttributeFilter {
		
		public AttributeDefinition getAppliesTo();
		
		public Operator getOperator();
		
		public String getOperand();
		
	}

}
