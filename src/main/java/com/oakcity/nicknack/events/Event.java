package com.oakcity.nicknack.events;

import java.util.Comparator;
import java.util.List;

import com.oakcity.nicknack.events.Event.AttributeDefinition.Unit;



/**
 * An event.
 * @author Kevin Bulebush
 *
 */
public interface Event {
	
	public List<Attribute<?,?>> getAttributes();
	
	public EventDefinition getEventDefinition();
	
	public interface EventDefinition {
		
		public String getName();
		
		// Group or 'parent'. The plugin or device that owns this.
		
		public List<AttributeDefinition<?, ?>> getAttributeDefinitions();
		
		public Event newInstance();
	}
	
	public interface Attribute<UnitType extends Unit<ValueType>, ValueType> {
		
		public AttributeDefinition<UnitType, ValueType> getAttributeDefinition();
		
		public ValueType getValue();

	}
	
	public interface AttributeDefinition<UnitType extends Unit<ValueType>, ValueType> {
		
		public Attribute<UnitType, ValueType> newInstance(ValueType value);
		
		public String getName();
		
		public String getDisplayValue(ValueType value);
		
		public UnitType getUnits();
		
		public interface Unit<ValueType> extends Comparator<ValueType> {
			
			public String getName();
			
			public Class<ValueType> getType();
			
			public ValueType getMin();
			
			public ValueType getMax();
			
			// Is this where we store acceptable ranges?
			
		}
	}
	
	
}
