package com.oakcity.nicknack.server.services.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oakcity.nicknack.core.actions.Action;
import com.oakcity.nicknack.server.model.ParameterRepository;
import com.oakcity.nicknack.server.model.ParameterResource;
import com.oakcity.nicknack.server.model.ActionRepository;
import com.oakcity.nicknack.server.services.ParametersService;

@Service
public class ParametersServiceImpl implements ParametersService {
	
	@Autowired
	private ActionRepository actionRepo;
	
	@Autowired
	private ParameterRepository parameterRepo;;

	@Override
	public List<ParameterResource> getParameters(UUID actionUuid) {
		final Action action = actionRepo.findOne(actionUuid);
		return parameterRepo.findByAction(action);
	}

	@Override
	public ParameterResource getParameter(UUID uuid) {
		return parameterRepo.findOne(uuid);
	}

	@Override
	public void deleteParameter(UUID uuid) {
		parameterRepo.delete(uuid);
	}

	@Override
	public ParameterResource createParameter(UUID actionUuid, ParameterResource newParameter) {
		final Action action = actionRepo.findOne(actionUuid);
		newParameter.setAction(action);
		
		final ParameterResource resource = parameterRepo.save(newParameter);
		return resource;
	}

	@Override
	public ParameterResource modifyParameter(ParameterResource modifiedParameter) {
		final ParameterResource resource = parameterRepo.save(modifiedParameter);
		return resource;
	}

}
