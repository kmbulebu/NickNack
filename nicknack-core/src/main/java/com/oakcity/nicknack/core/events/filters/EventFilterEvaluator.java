package com.oakcity.nicknack.core.events.filters;

import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.oakcity.nicknack.core.events.Event;
import com.oakcity.nicknack.core.events.Event.AttributeDefinition;
import com.oakcity.nicknack.core.events.filters.EventFilter.AttributeFilter;
import com.oakcity.nicknack.core.units.Unit;

public class EventFilterEvaluator {
	
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
		
		// All attribute filters have to match.
		for (AttributeFilter attributeFilter : eventFilter.getAttributeFilters()) {
			if (!evaluate(attributeFilter, event.getAttributes(), event.getEventDefinition().getAttributeDefinitions())) {
				if (LOG.isTraceEnabled()) {
					LOG.exit(false);
				}
				return false;
			}
		}
		
		// They all matched.
		if (LOG.isTraceEnabled()) {
			LOG.exit(true);
		}
		return true;
	}
	
	// Find an attribute definition that applies to a particular attribute filter.
	protected AttributeDefinition getApplicableAttributeDefinition(final AttributeFilter attributeFilter, final List<AttributeDefinition> attributeDefinitions) {
		if (LOG.isTraceEnabled()) {
			LOG.entry(attributeFilter, attributeDefinitions);
		}
		AttributeDefinition attributeDefinition = null;
		for (AttributeDefinition anAttributeDefinition : attributeDefinitions) {
			if (anAttributeDefinition.getUUID().equals(attributeFilter.getAppliesToAttributeDefinition())) {
				attributeDefinition = anAttributeDefinition;
				break;
			}
		}
		if (LOG.isTraceEnabled()) {
			LOG.exit(attributeDefinition);
		}
		return attributeDefinition;
	}
	
	// Evaluate single attribute filter
	protected boolean evaluate(final AttributeFilter attributeFilter, final Map<UUID, String> eventAttributes, final List<AttributeDefinition> attributeDefinitions) throws ParseException {
		if (LOG.isTraceEnabled()) {
			LOG.entry(attributeFilter, eventAttributes, attributeDefinitions);
		}
		// Find a matching attribute
		final String attributeValue = eventAttributes.get(attributeFilter.getAppliesToAttributeDefinition());
		if (attributeValue == null) {
			// No attribute value to match attribute filter.
			if (LOG.isTraceEnabled()) {
				LOG.trace("An attributeValue was not specified for this attribute definition.");
				LOG.exit(false);
			}
			return false;
		}
		
		// Find the Attribute definition that matches our filter.
		final AttributeDefinition attributeDefinition = getApplicableAttributeDefinition(attributeFilter, attributeDefinitions);
		
		if (attributeDefinition == null) {
			// Really shouldn't have any filters defined for attributes that don't exist.
			if (LOG.isTraceEnabled()) {
				LOG.trace("The attributeDefinition did not exist.");
				LOG.exit(false);
			}
			return false;
		}
		
		final Unit unit = attributeDefinition.getUnits();
		try {
			boolean result = unit.evaluate(attributeFilter.getOperator(), attributeValue, attributeFilter.getOperand());
			if (LOG.isTraceEnabled()) {
				LOG.exit(result);
			}
			return result;
		} catch (ParseException e) {
			if (LOG.isTraceEnabled()) {
				LOG.throwing(e);
			}
			throw e;
		}
	}
	
	

}
