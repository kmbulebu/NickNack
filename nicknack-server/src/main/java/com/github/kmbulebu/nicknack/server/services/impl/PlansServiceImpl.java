package com.github.kmbulebu.nicknack.server.services.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.kmbulebu.nicknack.server.model.PlanRepository;
import com.github.kmbulebu.nicknack.server.model.PlanResource;
import com.github.kmbulebu.nicknack.server.services.PlansService;
import com.github.kmbulebu.nicknack.server.services.exceptions.PlanNotFoundException;

@Service
public class PlansServiceImpl implements PlansService {
	
	@Autowired
	private PlanRepository planRepo;

	@Override
	public List<PlanResource> getPlans() {
		return planRepo.findAll();
	}

	@Override
	public PlanResource getPlan(UUID uuid) throws PlanNotFoundException {
		final PlanResource plan = planRepo.findOne(uuid);
		
		if (plan == null) {
			throw new PlanNotFoundException(uuid);
		}
		
		return plan;
	}
	
	private void throwIfNotExists(UUID uuid) throws PlanNotFoundException {
		if (!planRepo.exists(uuid)) {
			throw new PlanNotFoundException(uuid);
		}
	}

	@Override
	public void deletePlan(UUID uuid) throws PlanNotFoundException {
		throwIfNotExists(uuid);
		planRepo.delete(uuid);
	}

	@Override
	public PlanResource createPlan(PlanResource newPlan) {
		newPlan.setUuid(null);
		return planRepo.save(newPlan);
	}

	@Override
	public PlanResource modifyPlan(PlanResource modifiedPlan) throws PlanNotFoundException {
		final PlanResource existing = planRepo.findOne(modifiedPlan.getUUID());
		if (existing == null) {
			throw new PlanNotFoundException(modifiedPlan.getUUID());
		}
		modifiedPlan.setActions(existing.getActions());
		modifiedPlan.setEventFilters(existing.getEventFilters());
		return planRepo.save(modifiedPlan);
	}

}
