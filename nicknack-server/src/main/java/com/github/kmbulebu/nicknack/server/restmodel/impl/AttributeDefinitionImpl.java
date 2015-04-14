package com.github.kmbulebu.nicknack.server.restmodel.impl;

import java.util.UUID;

import com.github.kmbulebu.nicknack.server.restmodel.AttributeDefinition;

public class AttributeDefinitionImpl implements AttributeDefinition {

	private UUID uuid;
	private String name;
	private String description;
	private boolean required;
	private boolean multiValue;
	private ValueType valueType;
	private String[] choices;

	@Override
	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	@Override
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

	@Override
	public boolean isMultiValue() {
		return multiValue;
	}

	public void setMultiValue(boolean multiValue) {
		this.multiValue = multiValue;
	}

	@Override
	public ValueType getValueType() {
		return valueType;
	}

	public void setValueType(ValueType valueType) {
		this.valueType = valueType;
	}

	@Override
	public String[] getChoices() {
		return choices;
	}

	public void setChoices(String[] choices) {
		this.choices = choices;
	}

}
