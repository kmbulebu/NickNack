package com.github.kmbulebu.nicknack.core.attributes.filters;

import java.text.ParseException;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.github.kmbulebu.nicknack.core.attributes.AttributeCollection;
import com.github.kmbulebu.nicknack.core.attributes.AttributeDefinition;
import com.github.kmbulebu.nicknack.core.units.Unit;

/**
 * Evaluates of a collection of AttributeFilterExpressions matches
 * a collection of attributes.
 */
public class AttrbuteFilterExpressionCollectionEvaluator {
	
	private static final Logger LOG = LogManager.getLogger();
	
	/**
	 * 
	 * @param filterCollection
	 * @param attributeCollection
	 * @return True if all attribute filters match. False, if the event is not applicable to the filter, or at least one attribute filter does not match.
	 * @throws ParseException 
	 */
	
	public boolean evaluate(AttributeFilterExpressionCollection filterCollection, AttributeCollection attributeCollection, List<AttributeDefinition> attributeDefinitions) throws ParseException {
		if (LOG.isTraceEnabled()) {
			LOG.entry(filterCollection, attributeCollection);
		}
		
		// All attribute filters have to match.
		for (AttributeFilterExpression expression : filterCollection.getAttributeFilterExpressions()) {
			final UUID attributeDefinitionUuid = expression.getAttributeDefinitionUuid();
			if (!evaluate(attributeDefinitionUuid, expression, attributeCollection.getAttributes(), attributeDefinitions)) {
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
	protected AttributeDefinition getApplicableAttributeDefinition(final UUID attributeDefinitionUuid, final List<AttributeDefinition> attributeDefinitions) {
		if (LOG.isTraceEnabled()) {
			LOG.entry(attributeDefinitionUuid, attributeDefinitions);
		}
		AttributeDefinition attributeDefinition = null;
		for (AttributeDefinition anAttributeDefinition : attributeDefinitions) {
			if (anAttributeDefinition.getUUID().equals(attributeDefinitionUuid)) {
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
	protected boolean evaluate(final UUID attributeDefinitionUuid, final AttributeFilterExpression attributeFilter, final Map<UUID, String> eventAttributes, final List<AttributeDefinition> attributeDefinitions) throws ParseException {
		if (LOG.isTraceEnabled()) {
			LOG.entry(attributeDefinitionUuid, attributeFilter, eventAttributes, attributeDefinitions);
		}
		// Find a matching attribute
		final String attributeValue = eventAttributes.get(attributeDefinitionUuid);
		if (attributeValue == null) {
			// No attribute value to match attribute filter.
			if (LOG.isTraceEnabled()) {
				LOG.trace("An attributeValue was not specified for this attribute definition.");
				LOG.exit(false);
			}
			return false;
		}
		
		// Find the Attribute definition that matches our filter.
		final AttributeDefinition attributeDefinition = getApplicableAttributeDefinition(attributeDefinitionUuid, attributeDefinitions);
		
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
