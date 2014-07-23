package com.oakcity.nicknack.core.events.filters;

import java.util.List;
import java.util.UUID;


public interface EventFilter {
	
	public UUID getAppliesToEventDefinition();
	
	public String getDescription();
	
	public List<AttributeFilter> getAttributeFilters();

}
