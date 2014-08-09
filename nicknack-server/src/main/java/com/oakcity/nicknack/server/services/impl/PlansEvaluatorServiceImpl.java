package com.oakcity.nicknack.server.services.impl;

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

import com.oakcity.nicknack.core.actions.Action;
import com.oakcity.nicknack.core.actions.ActionFailureException;
import com.oakcity.nicknack.core.actions.ActionParameterException;
import com.oakcity.nicknack.core.events.AttributeDefinition;
import com.oakcity.nicknack.core.events.Event;
import com.oakcity.nicknack.core.events.filters.EventFilter;
import com.oakcity.nicknack.core.events.filters.EventFilterEvaluator;
import com.oakcity.nicknack.core.providers.ProviderService;
import com.oakcity.nicknack.server.AppConfiguration;
import com.oakcity.nicknack.server.model.ActionResource;
import com.oakcity.nicknack.server.model.EventFilterResource;
import com.oakcity.nicknack.server.model.PlanResource;
import com.oakcity.nicknack.server.services.ActionsService;
import com.oakcity.nicknack.server.services.EventFiltersService;
import com.oakcity.nicknack.server.services.PlansService;

@Service
public class PlansEvaluatorServiceImpl implements Action1<Event>{
	
	private static final Logger LOG = LogManager.getLogger(AppConfiguration.APP_LOGGER_NAME);
	
	@Autowired
	private ProviderService providerService;
	
	@Autowired
	private PlansService plansService;
	
	@Autowired
	private EventFiltersService eventFiltersService;
	
	@Autowired
	private ActionsService actionsService;
	
	private Subscription eventsSubscription;
	
	private EventFilterEvaluator eventFilterEvaluator = new EventFilterEvaluator();
	
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
		
		boolean match = false;
		
		final Iterator<EventFilterResource> iterator = eventFiltersService.getEventFilters(plan.getUUID()).iterator();
		
		// If any event filter matches, then this plan is match.
		while (!match && iterator.hasNext()) {
			final EventFilter filter = iterator.next();
			
			try {
				match = eventFilterEvaluator.evaluate(filter, event);
			} catch (ParseException e) {
				LOG.warn("Error parsing some text while evalutaing an event filter. " + e.getMessage(), e);
				// TODO Generate an error event
				match = false;
			}
			
			if (LOG.isInfoEnabled() && match) {
				LOG.info("EventFilter matched: " + ((EventFilterResource) filter).getUuid());
			}
		}
		
		if (match) {
			LOG.info("Plan " + plan.getUUID() + " matches event " + event);
			for (Action action : actionsService.getActions(plan.getUUID())) {
				LOG.info("Performing action " + action);
				try {
					providerService.run(processVariables(action, event));
				} catch (ActionFailureException | ActionParameterException e) {
					LOG.warn("Failed to perform action. " + e.getMessage() + " " + action);
					// TODO Generate and fire an Event for this error.
				} 
			}
		}
		
		if (LOG.isTraceEnabled()) {
			LOG.exit();
		}

	}
	
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
