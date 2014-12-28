package com.github.kmbulebu.nicknack.server.services.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.kmbulebu.nicknack.core.plans.Plan;
import com.github.kmbulebu.nicknack.server.model.StateFilterRepository;
import com.github.kmbulebu.nicknack.server.model.StateFilterResource;
import com.github.kmbulebu.nicknack.server.model.PlanRepository;
import com.github.kmbulebu.nicknack.server.model.PlanResource;
import com.github.kmbulebu.nicknack.server.services.StateFiltersService;

@Service
public class StateFiltersServiceImpl implements StateFiltersService {
	
	@Autowired
	private PlanRepository planRepo;
	
	@Autowired
	private StateFilterRepository stateFilterRepo;

	@Override
	public List<StateFilterResource> getStateFilters(UUID planUuid) {
		final Plan plan = planRepo.findOne(planUuid);
		return stateFilterRepo.findByPlan(plan);
	}

	@Override
	public StateFilterResource getStateFilter(UUID uuid) {
		return stateFilterRepo.findOne(uuid);
	}

	@Override
	public void deleteStateFilter(UUID uuid) {
		stateFilterRepo.delete(uuid);
	}

	@Override
	@Transactional
	public StateFilterResource createStateFilter(UUID planUuid, StateFilterResource newStateFilter) {
		final Plan plan = planRepo.findOne(planUuid);
		newStateFilter.setPlan(plan);
		
		final StateFilterResource resource = stateFilterRepo.save(newStateFilter);
		plan.getStateFilters().add(resource);
		planRepo.save((PlanResource) plan);
		
		return resource;
	}

	@Override
	public StateFilterResource modifyStateFilter(StateFilterResource modifiedStateFilter) {
		final StateFilterResource existing = stateFilterRepo.findOne(modifiedStateFilter.getUuid());
		modifiedStateFilter.setPlan(existing.getPlan());
		final StateFilterResource resource = stateFilterRepo.save(modifiedStateFilter);
		return resource;
	}

}
