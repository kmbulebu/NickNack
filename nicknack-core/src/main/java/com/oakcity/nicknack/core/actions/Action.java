package com.oakcity.nicknack.core.actions;

import java.util.List;
import java.util.UUID;

import com.oakcity.nicknack.core.Unit;


public interface Action {
	
	public interface ActionDefinition {
		
		public UUID getUUID();
		
		public String getName();
		
		public List<ParameterDefinition<?, ?>> getParameterDefinitions();
		
		public Action newInstance();
		
	}
	
	public interface Parameter<UnitType extends Unit<ValueType>, ValueType> {
		
		public ParameterDefinition<UnitType, ValueType> getParameterDefinition();
		
		public ValueType getValue();

	}
	
	public interface ParameterDefinition<UnitType extends Unit<ValueType>, ValueType> {
		
		public Parameter<UnitType, ValueType> newInstance(ValueType value);
		
		public String getName();
		
		public String getDisplayValue(ValueType value);
		
		public UnitType getUnits();

	}

}
