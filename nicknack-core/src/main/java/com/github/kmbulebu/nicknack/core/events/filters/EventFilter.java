package com.github.kmbulebu.nicknack.core.events.filters;

import java.util.Collection;
import java.util.UUID;


public interface EventFilter {
	
	public UUID getAppliesToEventDefinition();
	
	public String getDescription();
	
	public Collection<AttributeFilterExpression> getAttributeFilterExpressions();

}
