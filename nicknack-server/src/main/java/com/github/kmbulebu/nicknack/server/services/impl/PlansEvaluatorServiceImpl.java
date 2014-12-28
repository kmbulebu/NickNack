package com.github.kmbulebu.nicknack.server.services.impl;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.commons.lang3.text.StrSubstitutor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;

import com.github.kmbulebu.nicknack.core.actions.Action;
import com.github.kmbulebu.nicknack.core.attributes.AttributeDefinition;
import com.github.kmbulebu.nicknack.core.events.Event;
import com.github.kmbulebu.nicknack.core.events.filters.EventFilter;
import com.github.kmbulebu.nicknack.core.events.filters.EventFilterEvaluator;
import com.github.kmbulebu.nicknack.core.providers.Provider;
import com.github.kmbulebu.nicknack.core.providers.ProviderService;
import com.github.kmbulebu.nicknack.core.states.State;
import com.github.kmbulebu.nicknack.core.states.filters.StateFilter;
import com.github.kmbulebu.nicknack.core.states.filters.StateFilterEvaluator;
import com.github.kmbulebu.nicknack.server.Application;
import com.github.kmbulebu.nicknack.server.model.ActionResource;
import com.github.kmbulebu.nicknack.server.model.EventFilterResource;
import com.github.kmbulebu.nicknack.server.model.PlanResource;
import com.github.kmbulebu.nicknack.server.model.StateFilterResource;
import com.github.kmbulebu.nicknack.server.services.ActionQueueService;
import com.github.kmbulebu.nicknack.server.services.ActionsService;
import com.github.kmbulebu.nicknack.server.services.EventFiltersService;
import com.github.kmbulebu.nicknack.server.services.PlansService;
import com.github.kmbulebu.nicknack.server.services.StateFiltersService;

@Service
public class PlansEvaluatorServiceImpl implements Action1<Event> {
	
	private static final Logger LOG = LogManager.getLogger(Application.APP_LOGGER_NAME);
	
	@Autowired
	private ProviderService providerService;
	
	@Autowired
	private PlansService plansService;
	
	@Autowired
	private EventFiltersService eventFiltersService;
	
	@Autowired
	private StateFiltersService stateFiltersService;
	
	@Autowired
	private ActionsService actionsService;
	
	@Autowired
	private ActionQueueService actionQueueService;
	
	private Subscription eventsSubscription;
	
	private EventFilterEvaluator eventFilterEvaluator = new EventFilterEvaluator();
	
	private StateFilterEvaluator stateFilterEvaluator = new StateFilterEvaluator();
	
	@Autowired
	private NickNackServerProviderImpl nickNackServerProvider;
	
	@PostConstruct
	public void init() {
		if (LOG.isTraceEnabled()) {
			LOG.entry();
		}
		
		final Observable<Event> events = providerService.getEvents();
		eventsSubscription = events.subscribe(this);
		
		if (LOG.isTraceEnabled()) {
			LOG.exit();
		}
	}
	
	@PreDestroy
	public void shutdown() {
		if (LOG.isTraceEnabled()) {
			LOG.entry();
		}
		
		if (eventsSubscription != null) {
			eventsSubscription.unsubscribe();
		}
		
		if (LOG.isTraceEnabled()) {
			LOG.exit();
		}
	}

	@Override
	public void call(Event event) {
		if (LOG.isTraceEnabled()) {
			LOG.entry(event);
		}
		
		// TODO Do a more narrow lookup of plans that match the eventDefinition.
		final List<PlanResource> plans = plansService.getPlans();
		
		for (PlanResource plan : plans) {
			evaluate(plan, event);
		}
		
		if (LOG.isTraceEnabled()) {
			LOG.exit();
		}
	}
	
	public void evaluate(PlanResource plan, Event event) {
		if (LOG.isTraceEnabled()) {
			LOG.entry(plan, event);
		}
		
		boolean eventMatch = false;
		
		final Iterator<EventFilterResource> iterator = eventFiltersService.getEventFilters(plan.getUUID()).iterator();
		
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
				LOG.info("EventFilter matched: " + ((EventFilterResource) filter).getUuid());
			}
		}
		

		boolean stateMatch = false;
		
		final Iterator<StateFilterResource> stateFilterIterator = stateFiltersService.getStateFilters(plan.getUUID()).iterator();
		
		if (stateFilterIterator.hasNext()) {
			// If any state filter matches, then this plan is match.
			while (!stateMatch && stateFilterIterator.hasNext()) {
				final StateFilter filter = stateFilterIterator.next();
				
				final Provider provider = providerService.getProviderByStateDefinitionUuid(filter.getAppliesToStateDefinition());
				
				if (provider != null) {
					// FIXME getStates could be slow as the provider looks up the possible states. 
					final List<State> states = provider.getStates(filter.getAppliesToStateDefinition());
					
					if (states != null) {
						final Iterator<State> stateIterator = states.iterator();
						
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
					LOG.info("StateFilter matched: " + ((StateFilterResource) filter).getUuid());
				}
			} // Loop state filters
		} else {
			// State filters are not required. 
			stateMatch = true;
		}
		
		if (eventMatch && stateMatch) {
			LOG.info("Plan " + plan.getUUID() + " matches event " + event);
			for (Action action : actionsService.getActionsByPlan(plan.getUUID())) {
				LOG.info("Performing action " + action);
				final Action processedAction = processVariables(action, event);
				// TODO Group all actions together into a composite action so that they run serially, not in parallel.
				actionQueueService.enqueueAction(processedAction);
			}
		}
		
		if (LOG.isTraceEnabled()) {
			LOG.exit();
		}

	}
	
	// TODO I'd like to push a lot of this down into NickNack-core.
	
	protected Action processVariables(Action origAction, Event cause) {
		final ActionResource newAction = new ActionResource();
		final Map<UUID, String> newParameters = new HashMap<>();
		newAction.setAppliesToActionDefinition(origAction.getAppliesToActionDefinition());
		
		for (UUID key : origAction.getParameters().keySet()) {
			newParameters.put(key,  processVariables(origAction.getParameters().get(key), cause));
		}
		newAction.setParameters(newParameters);
		return newAction;
	}
	
	protected String processVariables(String value, Event cause) {
		// Build value map
		final Map<String, String> valueMap = new HashMap<>();
		final Map<UUID, AttributeDefinition> attributeMap = new HashMap<>();
		for (AttributeDefinition def : cause.getEventDefinition().getAttributeDefinitions()) {
			attributeMap.put(def.getUUID(), def);
		}
		for (UUID attributeUuid : cause.getAttributes().keySet()) {
			AttributeDefinition attribute = attributeMap.get(attributeUuid);
			valueMap.put(attribute.getName(), cause.getAttributes().get(attributeUuid));
		}
		final StrSubstitutor substitutor = new StrSubstitutor(valueMap, "{{", "}}");
		
		return substitutor.replace(value);
	}
	
	
	
	

}
