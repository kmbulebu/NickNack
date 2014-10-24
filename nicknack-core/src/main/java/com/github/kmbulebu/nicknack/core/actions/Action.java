package com.github.kmbulebu.nicknack.core.actions;

import java.util.Map;
import java.util.UUID;


public interface Action {
	
	public UUID getAppliesToActionDefinition();
	
	// Need to fulfill all the parameters specified in parameter definition.
	//public List<Parameter> getParameters();
	public Map<UUID, String> getParameters();
	
	
	
	/*public interface Parameter {
		
		public UUID getAppliesToParameterDefinition();
		
		public String getValue();

	}*/

}
