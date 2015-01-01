package com.github.kmbulebu.nicknack.core.states.filters;

import java.text.ParseException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.github.kmbulebu.nicknack.core.attributes.filters.AttrbuteFilterExpressionCollectionEvaluator;
import com.github.kmbulebu.nicknack.core.states.State;

public class StateFilterEvaluator extends AttrbuteFilterExpressionCollectionEvaluator {
	
	private static final Logger LOG = LogManager.getLogger();
	
	/**
	 * 
	 * @param stateFilter
	 * @param state
	 * @return True if all attribute filters match. False, if the state is not applicable to the filter, or at least one attribute filter does not match.
	 * @throws ParseException 
	 */
	
	public boolean evaluate(StateFilter stateFilter, State state) throws ParseException {
		if (LOG.isTraceEnabled()) {
			LOG.entry(stateFilter, state);
		}
		
		if (!stateFilter.getAppliesToStateDefinition().equals(state.getStateDefinition().getUUID())) {
			// Event is not applicable to this event filter.
			if (LOG.isTraceEnabled()) {
				LOG.trace("Event filter's appliesToEventDefinition value did not match Event Definition's UUID.");
				LOG.exit(false);
			}
			return false;
		}
		
		final boolean result = super.evaluate(stateFilter, state, state.getStateDefinition().getAttributeDefinitions());
		
		// They all matched.
		if (LOG.isTraceEnabled()) {
			LOG.exit(result);
		}
		return result;
	}
	
}
