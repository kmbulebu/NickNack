package com.github.kmbulebu.nicknack.server.restmodel;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonView;

public interface AttributeDefinition {
	
	@JsonView(View.Summary.class)
	public UUID getUuid();
	
	@JsonView(View.Summary.class)
	public String getName();

	public String getDescription();
	
	public boolean isRequired();

	@JsonView(View.Summary.class)
	public boolean isMultiValue();

	@JsonView(View.Summary.class)
	public ValueType getValueType();

	public String[] getChoices();
	
	public static class ValueType {
		
		@JsonView(View.Summary.class)
		private String name;
		private String isValidRegex;
		private Operator[] supportedOperators;
		
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getIsValidRegex() {
			return isValidRegex;
		}
		public void setIsValidRegex(String isValidRegex) {
			this.isValidRegex = isValidRegex;
		}
		public Operator[] getSupportedOperators() {
			return supportedOperators;
		}
		public void setSupportedOperators(Operator[] supportedOperators) {
			this.supportedOperators = supportedOperators;
		}
			
	}
	
	public static class Operator {
		
		private String name;
		private String description;
		private boolean arrayOperand;
		
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		public boolean isArrayOperand() {
			return arrayOperand;
		}
		public void setArrayOperand(boolean arrayOperand) {
			this.arrayOperand = arrayOperand;
		}
		
		
	}
	
}
