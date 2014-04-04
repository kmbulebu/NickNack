package com.oakcity.nicknack;

import java.util.Comparator;

public interface Unit<ValueType> extends Comparator<ValueType> {
	
	public String getName();
	
	public Class<ValueType> getType();
	
	public ValueType getMin();
	
	public ValueType getMax();
	
	// Is this where we store acceptable ranges?
	
}