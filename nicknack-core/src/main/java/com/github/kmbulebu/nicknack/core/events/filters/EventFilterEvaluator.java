package com.github.kmbulebu.nicknack.core.events.filters;

import java.text.ParseException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.github.kmbulebu.nicknack.core.attributes.filters.AttrbuteFilterExpressionCollectionEvaluator;
import com.github.kmbulebu.nicknack.core.events.Event;

public class EventFilterEvaluator extends AttrbuteFilterExpressionCollectionEvaluator {
	
	private static final Logger LOG = LogManager.getLogger();
	
	/**
	 * 
	 * @param eventFilter
	 * @param event
	 * @return True if all attribute filters match. False, if the event is not applicable to the filter, or at least one attribute filter does not match.
	 * @throws ParseException 
	 */
	
	public boolean evaluate(EventFilter eventFilter, Event event) throws ParseException {
		if (LOG.isTraceEnabled()) {
			LOG.entry(eventFilter, event);
		}
		
		if (!eventFilter.getAppliesToEventDefinition().equals(event.getEventDefinition().getUUID())) {
			// Event is not applicable to this event filter.
			if (LOG.isTraceEnabled()) {
				LOG.trace("Event filter's appliesToEventDefinition value did not match Event Definition's UUID.");
				LOG.exit(false);
			}
			return false;
		}
		
		final boolean result = super.evaluate(eventFilter, event, event.getEventDefinition().getAttributeDefinitions());
		
		// They all matched.
		if (LOG.isTraceEnabled()) {
			LOG.exit(result);
		}
		return result;
	}
	
}
