package com.github.kmbulebu.nicknack.core.events.filters;

import java.util.UUID;

import com.github.kmbulebu.nicknack.core.attributes.filters.AttributeFilterExpressionCollection;


public interface EventFilter extends AttributeFilterExpressionCollection {
	
	public UUID getAppliesToEventDefinition();
	
	public String getDescription();

}
