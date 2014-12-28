package com.github.kmbulebu.nicknack.core.attributes.filters;


public enum Operator {
	
	EQUALS("=", "The values are the same."),
	IN("in", "Equals any value in the comma separated list of values. Spaces are ignored."),
	NOT_IN("not in", "Not equal to any value in the comma separated list of values. Spaces are ignored."),
	EVERY("every", "The value divides evenly by this amount.");
	
	
	private final String name;
	private final String description;
	
	Operator(String name, String description) {
		this.name = name;
		this.description = description;
	}
	
	public String getName() {
		return name;
	}
	
	public String getDescription() {
		return description;
	}
	


}
