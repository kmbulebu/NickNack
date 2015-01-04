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
import com.github.kmbulebu.nicknack.server.services.exceptions.PlanNotFoundException;
import com.github.kmbulebu.nicknack.server.services.exceptions.StateFilterNotFoundException;

@Service
public class StateFiltersServiceImpl implements StateFiltersService {
	
	@Autowired
	private PlanRepository planRepo;
	
	@Autowired
	private StateFilterRepository stateFilterRepo;

	@Override
	public List<StateFilterResource> getStateFilters(UUID planUuid) throws PlanNotFoundException {
		final Plan plan = planRepo.findOne(planUuid);
		
		if (plan == null) {
			throw new PlanNotFoundException(planUuid);
		}
		return stateFilterRepo.findByPlan(plan);
	}

	@Override
	public StateFilterResource getStateFilter(UUID uuid) throws StateFilterNotFoundException {
		final StateFilterResource filter = stateFilterRepo.findOne(uuid);
	
		if (filter == null) {
			throw new StateFilterNotFoundException(uuid);
		}
		return filter;
	}
	
	private void throwIfNotExists(UUID uuid) throws StateFilterNotFoundException {
		if (!stateFilterRepo.exists(uuid)) {
			throw new StateFilterNotFoundException(uuid);
		}
	}

	@Override
	public void deleteStateFilter(UUID uuid) throws StateFilterNotFoundException {
		throwIfNotExists(uuid);
		stateFilterRepo.delete(uuid);
	}

	@Override
	@Transactional
	public StateFilterResource createStateFilter(UUID planUuid, StateFilterResource newStateFilter) throws PlanNotFoundException {
		final Plan plan = planRepo.findOne(planUuid);
		if (plan == null) {
			throw new PlanNotFoundException(planUuid);
		}
		
		newStateFilter.setPlan(plan);
		
		final StateFilterResource resource = stateFilterRepo.save(newStateFilter);
		plan.getStateFilters().add(resource);
		planRepo.save((PlanResource) plan);
		
		return resource;
	}

	@Override
	public StateFilterResource modifyStateFilter(StateFilterResource modifiedStateFilter) throws StateFilterNotFoundException {
		final StateFilterResource existing = stateFilterRepo.findOne(modifiedStateFilter.getUuid());
		if (existing == null) {
			throw new StateFilterNotFoundException(modifiedStateFilter.getUuid());
		}
		modifiedStateFilter.setPlan(existing.getPlan());
		final StateFilterResource resource = stateFilterRepo.save(modifiedStateFilter);
		return resource;
	}

}
