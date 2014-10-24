package com.github.kmbulebu.nicknack.server.services.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.kmbulebu.nicknack.server.model.PlanRepository;
import com.github.kmbulebu.nicknack.server.model.PlanResource;
import com.github.kmbulebu.nicknack.server.services.PlansService;

@Service
public class PlansServiceImpl implements PlansService {
	
	@Autowired
	private PlanRepository planRepo;

	@Override
	public List<PlanResource> getPlans() {
		return planRepo.findAll();
	}

	@Override
	public PlanResource getPlan(UUID uuid) {
		return planRepo.findOne(uuid);
	}

	@Override
	public void deletePlan(UUID uuid) {
		planRepo.delete(uuid);
	}

	@Override
	public PlanResource createPlan(PlanResource newPlan) {
		newPlan.setUuid(null);
		return planRepo.save(newPlan);
	}

	@Override
	public PlanResource modifyPlan(PlanResource modifiedPlan) {
		final PlanResource existing = planRepo.findOne(modifiedPlan.getUUID());
		modifiedPlan.setActions(existing.getActions());
		modifiedPlan.setEventFilters(existing.getEventFilters());
		return planRepo.save(modifiedPlan);
	}

}
