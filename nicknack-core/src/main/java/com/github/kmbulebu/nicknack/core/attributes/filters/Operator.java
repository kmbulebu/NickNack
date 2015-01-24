package com.github.kmbulebu.nicknack.core.attributes.filters;

/**
 * Operators available for creating attribute filters in 
 * event filters and state filters.
 *
 */
public enum Operator {
	
	EQUALS("=", "The values are the same.", false),
	IN("in", "Equals any value in the comma separated list of values. Spaces are ignored.", true),
	NOT_IN("not in", "Not equal to any value in the comma separated list of values. Spaces are ignored.", true);
	
	private final String name;
	private final String description;
	private final boolean operandIsArray;
	
	Operator(String name, String description, boolean operandIsArray) {
		this.name = name;
		this.description = description;
		this.operandIsArray = operandIsArray;
	}
	
	public String getName() {
		return name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public boolean isOperandIsArray() {
		return operandIsArray;
	}


}
