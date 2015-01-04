package com.github.kmbulebu.nicknack.server.services;

import java.util.List;
import java.util.UUID;

import com.github.kmbulebu.nicknack.server.model.PlanResource;
import com.github.kmbulebu.nicknack.server.services.exceptions.PlanNotFoundException;

public interface PlansService {
	
	public List<PlanResource> getPlans();
	
	public PlanResource getPlan(UUID uuid) throws PlanNotFoundException;
	
	public void deletePlan(UUID uuid) throws PlanNotFoundException;
	
	public PlanResource createPlan(PlanResource newPlan);
	
	public PlanResource modifyPlan(PlanResource modifiedPlan) throws PlanNotFoundException;

}
