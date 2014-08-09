package com.oakcity.nicknack.core.actions;

import java.util.List;
import java.util.UUID;

public interface ActionDefinition {
	
	public UUID getUUID();
	
	public UUID getProviderUUID();
	
	public String getName();
	
	public List<ParameterDefinition> getParameterDefinitions();
	
	//public void run(Action action) throws ActionFailureException, ActionParameterException;
	
}