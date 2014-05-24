package com.oakcity.nicknack.core.actions;

import java.util.List;
import java.util.UUID;

import com.oakcity.nicknack.core.Unit;


public interface Action {
	
	public interface ActionDefinition {
		
		public UUID getUUID();
		
		public String getName();
		
		public List<ParameterDefinition> getParameterDefinitions();
		
	}
	
	public interface Parameter<UnitType extends Unit<ValueType>, ValueType> {
		
		//public ParameterDefinition<UnitType, ValueType> getParameterDefinition();
		
		public ValueType getValue();

	}
	
	public interface ParameterDefinition {
		
		public UUID getUUID();
		
		public String getName();
		
		public Unit<?> getUnits();
		
		public boolean isRequired();

	}

}
