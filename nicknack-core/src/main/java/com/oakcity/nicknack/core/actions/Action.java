package com.oakcity.nicknack.core.actions;

import java.text.ParseException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.oakcity.nicknack.core.units.Unit;


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
		
		public void run(Action action) throws ActionFailureException, ActionParameterException;
		
	}
	
	/*public interface Parameter {
		
		public UUID getAppliesToParameterDefinition();
		
		public String getValue();

	}*/
	
	public interface ParameterDefinition {
		
		public UUID getUUID();
		
		public String getName();
		
		public Unit getUnits();
		
		public boolean isRequired();
		
		// Cleans up a user enter value. 
		public String format(String rawValue) throws ParseException;
		
		// Returns an empty collection for a valid value, or a collection of error messages.
		public Collection<String> validate(String value);
		
	}

}
