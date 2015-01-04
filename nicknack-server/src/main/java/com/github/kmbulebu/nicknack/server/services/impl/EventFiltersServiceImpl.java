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
import com.github.kmbulebu.nicknack.server.services.exceptions.EventFilterNotFoundException;
import com.github.kmbulebu.nicknack.server.services.exceptions.PlanNotFoundException;

@Service
public class EventFiltersServiceImpl implements EventFiltersService {
	
	@Autowired
	private PlanRepository planRepo;
	
	@Autowired
	private EventFilterRepository eventFilterRepo;;

	@Override
	public List<EventFilterResource> getEventFilters(UUID planUuid) throws PlanNotFoundException {
		final Plan plan = planRepo.findOne(planUuid);
		
		if (plan == null) {
			throw new PlanNotFoundException(planUuid);
		}
		return eventFilterRepo.findByPlan(plan);
	}

	@Override
	public EventFilterResource getEventFilter(UUID uuid) throws EventFilterNotFoundException {
		final EventFilterResource eventFilter = eventFilterRepo.findOne(uuid);
		if (eventFilter == null) {
			throw new EventFilterNotFoundException(uuid);
		}
		return eventFilter;
	}
	
	private void throwIfNotExists(UUID eventFilterUuid) throws EventFilterNotFoundException {
		if (!eventFilterRepo.exists(eventFilterUuid)) {
			throw new EventFilterNotFoundException(eventFilterUuid);
		}
	}

	@Override
	public void deleteEventFilter(UUID uuid) throws EventFilterNotFoundException {
		throwIfNotExists(uuid);
		eventFilterRepo.delete(uuid);
	}

	@Override
	@Transactional
	public EventFilterResource createEventFilter(UUID planUuid, EventFilterResource newEventFilter) throws PlanNotFoundException {
		final Plan plan = planRepo.findOne(planUuid);
		
		if (plan == null) {
			throw new PlanNotFoundException(planUuid);
		}
		newEventFilter.setPlan(plan);
		
		final EventFilterResource resource = eventFilterRepo.save(newEventFilter);
		plan.getEventFilters().add(resource);
		planRepo.save((PlanResource) plan);
		
		return resource;
	}

	@Override
	public EventFilterResource modifyEventFilter(EventFilterResource modifiedEventFilter) throws EventFilterNotFoundException {
		final EventFilterResource existing = eventFilterRepo.findOne(modifiedEventFilter.getUuid());
		if (existing == null) {
			throw new EventFilterNotFoundException(modifiedEventFilter.getUuid());
		}
		
		modifiedEventFilter.setPlan(existing.getPlan());
		final EventFilterResource resource = eventFilterRepo.save(modifiedEventFilter);
		return resource;
	}

}
