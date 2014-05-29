package com.oakcity.nicknack.server.services.impl;

import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;

import com.oakcity.nicknack.core.actions.Action;
import com.oakcity.nicknack.core.events.Event;
import com.oakcity.nicknack.core.events.filters.EventFilter;
import com.oakcity.nicknack.core.events.filters.EventFilterEvaluator;
import com.oakcity.nicknack.core.providers.ProviderService;
import com.oakcity.nicknack.server.AppConfiguration;
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
		while (!match && iterator.hasNext()) {
			final EventFilter filter = iterator.next();
			
			if (eventFilterEvaluator.evaluate(filter, event)) {
				// LOG
				match = true;
			}
		}
		
		if (match) {
			LOG.info("Plan " + plan.getUUID() + " matches event " + event);
			for (Action action : actionsService.getActions(plan.getUUID())) {
				LOG.info("Performing action " + action);
				providerService.run(action);
			}
		}
		
		if (LOG.isTraceEnabled()) {
			LOG.exit();
		}

	}

}
