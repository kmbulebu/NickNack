package com.oakcity.nicknack.core.actions;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.oakcity.nicknack.core.Unit;


public interface Action {
	
	public UUID getAppliesToActionDefinition();
	
	// Need to fulfill all the parameters specified in parameter definition.
	//public List<Parameter> getParameters();
	public Map<UUID, String> getParameters();
	
	public interface ActionDefinition {
		
		public UUID getUUID();
		
		public UUID getProviderUUID();
		
		public String getName();
		
		public List<ParameterDefinition> getParameterDefinitions();
		
		// TODO Create some exceptions to differentiate problems with user input (bad parameters) vs problems during execution of the action.
		public void run(Action action);
		
	}
	
	/*public interface Parameter {
		
		public UUID getAppliesToParameterDefinition();
		
		public String getValue();

	}*/
	
	public interface ParameterDefinition {
		
		public UUID getUUID();
		
		public String getName();
		
		public Unit<?> getUnits();
		
		public boolean isRequired();

	}

}
