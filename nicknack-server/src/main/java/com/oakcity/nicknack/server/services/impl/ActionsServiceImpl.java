package com.oakcity.nicknack.server.services.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oakcity.nicknack.core.plans.Plan;
import com.oakcity.nicknack.server.model.ActionRepository;
import com.oakcity.nicknack.server.model.ActionResource;
import com.oakcity.nicknack.server.model.PlanRepository;
import com.oakcity.nicknack.server.model.PlanResource;
import com.oakcity.nicknack.server.services.ActionsService;

@Service
public class ActionsServiceImpl implements ActionsService {
	
	@Autowired
	private PlanRepository planRepo;
	
	@Autowired
	private ActionRepository actionRepo;

	@Override
	public List<ActionResource> getActions(UUID planUuid) {
		final Plan plan = planRepo.findOne(planUuid);
		return actionRepo.findByPlan(plan);
	}

	@Override
	public ActionResource getAction(UUID uuid) {
		return actionRepo.findOne(uuid);
	}

	@Override
	public void deleteAction(UUID uuid) {
		actionRepo.delete(uuid);
	}

	@Override
	@Transactional
	public ActionResource createAction(UUID planUuid, ActionResource newAction) {
		final Plan plan = planRepo.findOne(planUuid);
		newAction.setPlan(plan);
		
		final ActionResource resource = actionRepo.save(newAction);
		plan.getActions().add(resource);
		
		planRepo.save((PlanResource) plan);
		return resource;
	}

	@Override
	public ActionResource modifyAction(ActionResource modifiedAction) {
		final ActionResource resource = actionRepo.save(modifiedAction);
		return resource;
	}

}
