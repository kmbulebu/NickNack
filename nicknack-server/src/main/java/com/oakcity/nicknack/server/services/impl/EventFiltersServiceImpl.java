package com.oakcity.nicknack.server.services.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oakcity.nicknack.core.plans.Plan;
import com.oakcity.nicknack.server.model.EventFilterRepository;
import com.oakcity.nicknack.server.model.EventFilterResource;
import com.oakcity.nicknack.server.model.PlanRepository;
import com.oakcity.nicknack.server.model.PlanResource;
import com.oakcity.nicknack.server.services.EventFiltersService;

@Service
public class EventFiltersServiceImpl implements EventFiltersService {
	
	@Autowired
	private PlanRepository planRepo;
	
	@Autowired
	private EventFilterRepository eventFilterRepo;;

	@Override
	public List<EventFilterResource> getEventFilters(UUID planUuid) {
		final Plan plan = planRepo.findOne(planUuid);
		return eventFilterRepo.findByPlan(plan);
	}

	@Override
	public EventFilterResource getEventFilter(UUID uuid) {
		return eventFilterRepo.findOne(uuid);
	}

	@Override
	public void deleteEventFilter(UUID uuid) {
		eventFilterRepo.delete(uuid);
	}

	@Override
	@Transactional
	public EventFilterResource createEventFilter(UUID planUuid, EventFilterResource newEventFilter) {
		final Plan plan = planRepo.findOne(planUuid);
		newEventFilter.setPlan(plan);
		
		final EventFilterResource resource = eventFilterRepo.save(newEventFilter);
		plan.getEventFilters().add(resource);
		planRepo.save((PlanResource) plan);
		
		return resource;
	}

	@Override
	public EventFilterResource modifyEventFilter(EventFilterResource modifiedEventFilter) {
		final EventFilterResource existing = eventFilterRepo.findOne(modifiedEventFilter.getUuid());
		modifiedEventFilter.setPlan(existing.getPlan());
		modifiedEventFilter.setAttributeFilters(existing.getAttributeFilters());
		final EventFilterResource resource = eventFilterRepo.save(modifiedEventFilter);
		return resource;
	}

}
