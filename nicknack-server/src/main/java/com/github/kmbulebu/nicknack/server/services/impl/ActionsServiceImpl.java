package com.github.kmbulebu.nicknack.server.services.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.kmbulebu.nicknack.server.model.ActionRepository;
import com.github.kmbulebu.nicknack.server.model.ActionResource;
import com.github.kmbulebu.nicknack.server.model.PlanRepository;
import com.github.kmbulebu.nicknack.server.model.PlanResource;
import com.github.kmbulebu.nicknack.server.services.ActionsService;

@Service
public class ActionsServiceImpl implements ActionsService {
	
	@Autowired
	private PlanRepository planRepo;
	
	@Autowired
	private ActionRepository actionRepo;

	@Override
	@Transactional(readOnly=true)
	public List<ActionResource> getActionsByPlan(UUID planUuid) {
		final PlanResource plan = planRepo.findOne(planUuid);
		return actionRepo.findByPlan(plan);
	}

	@Override
	@Transactional(readOnly=true)
	public ActionResource getAction(UUID uuid) {
		return actionRepo.findOne(uuid);
	}

	@Override
	@Transactional(readOnly=true)
	public void deleteAction(UUID uuid) {
		actionRepo.delete(uuid);
	}

	@Override
	@Transactional
	public ActionResource modifyAction(ActionResource modifiedAction) {
		final ActionResource existing = actionRepo.findOne(modifiedAction.getUuid());
		modifiedAction.setPlans(existing.getPlans());
		final ActionResource resource = actionRepo.save(modifiedAction);
		return resource;
	}

	@Override
	@Transactional
	public ActionResource createAction(ActionResource newActionBookmark) {
		final ActionResource resource = actionRepo.save(newActionBookmark);
		return resource;
	}

	@Override
	@Transactional(readOnly=true)
	public List<ActionResource> getActions() {
		return actionRepo.findAll();
	}

	@Override
	@Transactional
	public void addActionToPlan(UUID planUuid, UUID actionUuid) {
		final ActionResource action = actionRepo.findOne(actionUuid);
		final PlanResource plan = planRepo.findOne(planUuid);
		
		plan.getActions().add(action);
		
		action.getPlans().add(plan);
		
		planRepo.save(plan);
		actionRepo.save(action);	
	}

	@Override
	public void deleteAction(UUID planUuid, UUID actionUuid) {
		final ActionResource action = actionRepo.findOne(actionUuid);
		final PlanResource plan = planRepo.findOne(planUuid);
		
		plan.getActions().remove(action);
		action.getPlans().remove(plan);
		
		planRepo.save(plan);
		if (action.getPlans().isEmpty()) {
			actionRepo.delete(action);
		}
	}

}
