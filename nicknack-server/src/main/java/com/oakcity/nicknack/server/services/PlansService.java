package com.oakcity.nicknack.server.services;

import java.util.List;
import java.util.UUID;

import com.oakcity.nicknack.server.model.PlanResource;

public interface PlansService {
	
	public List<PlanResource> getPlans();
	
	public PlanResource getPlan(UUID uuid);
	
	public void deletePlan(UUID uuid);
	
	public PlanResource createPlan(PlanResource newPlan);
	
	public PlanResource modifyPlan(PlanResource modifiedPlan);

}
