package com.github.kmbulebu.nicknack.core.plans;

import java.text.ParseException;
import java.util.Iterator;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.github.kmbulebu.nicknack.core.events.Event;
import com.github.kmbulebu.nicknack.core.events.filters.EventFilter;
import com.github.kmbulebu.nicknack.core.events.filters.EventFilterEvaluator;
import com.github.kmbulebu.nicknack.core.providers.Provider;
import com.github.kmbulebu.nicknack.core.providers.ProviderService;
import com.github.kmbulebu.nicknack.core.states.State;
import com.github.kmbulebu.nicknack.core.states.filters.StateFilter;
import com.github.kmbulebu.nicknack.core.states.filters.StateFilterEvaluator;

public class PlanEvaluator {
	
	private static final Logger LOG = LogManager.getLogger();
	
	private final EventFilterEvaluator eventFilterEvaluator = new EventFilterEvaluator();
	private final StateFilterEvaluator stateFilterEvaluator = new StateFilterEvaluator();
	
	private final ProviderService providerService;
	
	public PlanEvaluator(ProviderService providerService) {
		this.providerService = providerService;
	}
	
	public boolean evaluate(Plan plan, Event event) {
		if (LOG.isTraceEnabled()) {
			LOG.entry(plan, event);
		}
		
		boolean eventMatch = false;
		
		final Iterator<EventFilter> iterator = plan.getEventFilters().iterator();
		
		// If any event filter matches, then this plan is match.
		while (!eventMatch && iterator.hasNext()) {
			final EventFilter filter = iterator.next();
			
			try {
				eventMatch = eventFilterEvaluator.evaluate(filter, event);
			} catch (ParseException e) {
				LOG.warn("Error parsing some text while evalutaing an event filter. " + e.getMessage(), e);
				// TODO Generate an error event
				eventMatch = false;
			}
			
			if (LOG.isInfoEnabled() && eventMatch) {
				LOG.info("EventFilter matched: " + filter.toString());
			}
		}
		
		boolean stateMatch = false;
		if (eventMatch) {// Only test state if event matches
			final Iterator<StateFilter> stateFilterIterator = plan.getStateFilters().iterator();
			
			if (stateFilterIterator.hasNext()) {
				// If any state filter matches, then this plan is match.
				while (!stateMatch && stateFilterIterator.hasNext()) {
					final StateFilter filter = stateFilterIterator.next();
					
					final Provider provider = providerService.getProviderByStateDefinitionUuid(filter.getAppliesToStateDefinition());
					
					if (provider != null) {
						// FIXME getStates could be slow as the provider looks up the possible states. 
						final List<? extends State> states = provider.getStates(filter.getAppliesToStateDefinition());
						
						if (states != null) {
							final Iterator<? extends State> stateIterator = states.iterator();
							
							while (!stateMatch && stateIterator.hasNext()) {
								final State state = stateIterator.next();
								try {
									stateMatch = stateFilterEvaluator.evaluate(filter, state);
								} catch (ParseException e) {
									LOG.warn("Error parsing some text while evalutaing an state filter. " + e.getMessage(), e);
									// TODO Generate an error state
									stateMatch = false;
								}
							} // State loop
						} // If states != null
					} // If provider != null
					
					if (LOG.isInfoEnabled() && stateMatch) {
						LOG.info("StateFilter matched: " + filter.toString());
					}
				} // Loop state filters
			} else {
				// State filters are not required. 
				stateMatch = true;
			}
		}
		
		final boolean planMatches = eventMatch && stateMatch;
		
		if (LOG.isTraceEnabled()) {
			LOG.exit(planMatches);
		}
		return planMatches;

	}

}
