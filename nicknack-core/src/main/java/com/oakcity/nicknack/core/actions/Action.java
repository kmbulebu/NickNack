package com.oakcity.nicknack.core.actions;

import java.util.List;
import java.util.UUID;

import com.oakcity.nicknack.core.Unit;


public interface Action {
	
	public UUID getAppliesToActionDefinition();
	
	// Need to fulfill all the parameters specified in parameter definition.
	public List<Parameter> getParameters();
	
	public interface ActionDefinition {
		
		public UUID getUUID();
		
		public String getName();
		
		public List<ParameterDefinition> getParameterDefinitions();
		
	}
	
	public interface Parameter {
		
		public UUID getAppliesToParameterDefinition();
		
		public String getValue();

	}
	
	public interface ParameterDefinition {
		
		public UUID getUUID();
		
		public String getName();
		
		public Unit<?> getUnits();
		
		public boolean isRequired();

	}

}
