package com.oakcity.nicknack.server.services;

import java.util.List;
import java.util.UUID;

import com.oakcity.nicknack.server.model.ParameterResource;

public interface ParametersService {
	
	public List<ParameterResource> getParameters(UUID actionUuid);
	
	public ParameterResource getParameter(UUID uuid);
	
	public void deleteParameter(UUID uuid);
	
	public ParameterResource createParameter(UUID actionUuid, ParameterResource newParameter);
	
	public ParameterResource modifyParameter(ParameterResource modifiedParameter);

}
