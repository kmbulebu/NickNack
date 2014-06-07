package com.oakcity.nicknack.core.events.filters;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.oakcity.nicknack.core.Unit;
import com.oakcity.nicknack.core.events.Event;
import com.oakcity.nicknack.core.events.Event.AttributeDefinition;
import com.oakcity.nicknack.core.events.filters.EventFilter.AttributeFilter;

public class EventFilterEvaluator {
	
	/**
	 * 
	 * @param eventFilter
	 * @param event
	 * @return True if all attribute filters match. False, if the event is not applicable to the filter, or at least one attribute filter does not match.
	 */
	
	public boolean evaluate(EventFilter eventFilter, Event event) {
		if (!eventFilter.getAppliesToEventDefinition().equals(event.getEventDefinition().getUUID())) {
			// Event is not applicable to this event filter.
			return false;
		}
		
		// All attribute filters have to match.
		for (AttributeFilter attributeFilter : eventFilter.getAttributeFilters()) {
			if (!evaluate(attributeFilter, event.getAttributes(), event.getEventDefinition().getAttributeDefinitions())) {
				return false;
			}
		}
		
		// They all matched.
		return true;
	}
	
	// Find an attribute definition that applies to a particular attribute filter.
	protected AttributeDefinition getApplicableAttributeDefinition(final AttributeFilter attributeFilter, final List<AttributeDefinition> attributeDefinitions) {
		AttributeDefinition attributeDefinition = null;
		for (AttributeDefinition anAttributeDefinition : attributeDefinitions) {
			if (anAttributeDefinition.getUUID().equals(attributeFilter.getAppliesToAttributeDefinition())) {
				attributeDefinition = anAttributeDefinition;
				break;
			}
		}
		return attributeDefinition;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	// Evaluate single attribute filter
	protected boolean evaluate(final AttributeFilter attributeFilter, final Map<UUID, String> eventAttributes, final List<AttributeDefinition> attributeDefinitions) {
		// Find a matching attribute
		final String attributeValue = eventAttributes.get(attributeFilter.getAppliesToAttributeDefinition());
		if (attributeValue == null) {
			// No attribute value to match attribute filter.
			return false;
		}
		
		// Find the Attribute definition that matches our filter.
		final AttributeDefinition attributeDefinition = getApplicableAttributeDefinition(attributeFilter, attributeDefinitions);
		
		if (attributeDefinition == null) {
			// Really shouldn't have any filters defined for attributes that don't exist.
			return false;
		}
		
		final Unit unit = attributeDefinition.getUnits();
		return unit.evaluate(attributeFilter.getOperator(), unit.parse(attributeValue), unit.parse(attributeFilter.getOperand()));
	}
	
	

}
