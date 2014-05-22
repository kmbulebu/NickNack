package com.oakcity.nicknack.events.filters.operators;


public enum Operator {
	
	EQUALS("=", "The values are the same.");
	
	
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
