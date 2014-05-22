package com.oakcity.nicknack.core.events.filters;

import com.oakcity.nicknack.core.Unit;
import com.oakcity.nicknack.core.events.Event;
import com.oakcity.nicknack.core.events.filters.EventFilter.AttributeFilter;

public class EventFilterEvaluator {
	
	/**
	 * 
	 * @param eventFilter
	 * @param event
	 * @return True if all attribute filters match. False, if the event is not applicable to the filter, or at least one attribute filter does not match.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public boolean evaluate(EventFilter eventFilter, Event event) {
		if (!eventFilter.getAppliesTo().equals(event.getEventDefinition())) {
			// Event is not applicable to this event filter.
			return false;
		}
		
		
		for (AttributeFilter attributeFilter : eventFilter.getAttributeFilters()) {
			// Find a matching attribute
			final String attributeValue = event.getAttributes().get(attributeFilter.getAppliesTo());
			if (attributeValue == null) {
				// No matching event;
				return false;
			}
			
			final Unit unit = attributeFilter.getAppliesTo().getUnits();
			if (!unit.evaluate(attributeFilter.getOperator(), unit.parse(attributeValue), unit.parse(attributeFilter.getOperand()))) {
				return false;
			}
			
		}
		
		return true;
	}

}
