package com.oakcity.nicknack.actions;

import java.util.List;

import com.oakcity.nicknack.Unit;


public interface Action {
	
	public interface ActionDefinition {
		
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
