package com.oakcity.nicknack;

import java.util.Comparator;

import com.oakcity.nicknack.events.filters.operators.Operator;

public interface Unit<ValueType> extends Comparator<ValueType> {
	
	public String getName();
	
	public Class<ValueType> getType();
	
	public ValueType getMin();
	
	public ValueType getMax();
	
	public ValueType parse(String input);
	
	public String toString(ValueType input);
	
	// REturns null if the operator is not supported by this unit.
	public Boolean evaluate(Operator operator, ValueType operand1, ValueType operand2);
	
	public Operator[] getSupportedOperators();
	
	// Is this where we store acceptable ranges?
	
}