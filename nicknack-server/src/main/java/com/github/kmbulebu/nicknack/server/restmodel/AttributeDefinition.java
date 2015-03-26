package com.github.kmbulebu.nicknack.server.restmodel;

import java.util.UUID;

public class AttributeDefinition {

	private UUID uuid;
	private String name;
	private String description;
	private boolean required;
	private boolean multiValue;
	private ValueType valueType;
	private String[] choices;
	
	public UUID getUuid() {
		return uuid;
	}
	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}
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
	public boolean isRequired() {
		return required;
	}
	public void setRequired(boolean required) {
		this.required = required;
	}
	public boolean isMultiValue() {
		return multiValue;
	}
	public void setMultiValue(boolean multiValue) {
		this.multiValue = multiValue;
	}
	public ValueType getValueType() {
		return valueType;
	}
	public void setValueType(ValueType valueType) {
		this.valueType = valueType;
	}
	public String[] getChoices() {
		return choices;
	}
	public void setChoices(String[] choices) {
		this.choices = choices;
	}
	

	public static class ValueType {
		
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
