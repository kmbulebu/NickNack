package com.github.kmbulebu.nicknack.server.services.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.kmbulebu.nicknack.core.plans.Plan;
import com.github.kmbulebu.nicknack.server.model.EventFilterRepository;
import com.github.kmbulebu.nicknack.server.model.EventFilterResource;
import com.github.kmbulebu.nicknack.server.model.PlanRepository;
import com.github.kmbulebu.nicknack.server.model.PlanResource;
import com.github.kmbulebu.nicknack.server.services.EventFiltersService;

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
		final EventFilterResource resource = eventFilterRepo.save(modifiedEventFilter);
		return resource;
	}

}
