package com.github.kmbulebu.nicknack.core.states.filters;

import java.util.UUID;

import com.github.kmbulebu.nicknack.core.attributes.filters.AttributeFilterExpressionCollection;


public interface StateFilter extends AttributeFilterExpressionCollection  {
	
	public UUID getAppliesToStateDefinition();
	
	public String getDescription();

}
