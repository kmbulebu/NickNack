package com.github.kmbulebu.nicknack.server.restmodel.impl;

import java.util.UUID;

import com.github.kmbulebu.nicknack.server.restmodel.Attribute;
import com.github.kmbulebu.nicknack.server.restmodel.AttributeDefinition;

public class AttributeImpl implements Attribute {
	
	private final AttributeDefinition attributeDefinition;
	private String[] values = new String[]{};
	
	public AttributeImpl(AttributeDefinition attributeDefinition) {
		this.attributeDefinition = attributeDefinition;
	}
	
	public String[] getValues() {
		return values;
	}
	
	public void setValues(String[] values) {
		this.values = values;
	}
	
	public void setValue(String value) {
		this.values = new String[]{value};
	}

	@Override
	public UUID getUuid() {
		return attributeDefinition.getUuid();
	}

	@Override
	public String getName() {
		return attributeDefinition.getName();
	}

	@Override
	public String getDescription() {
		return attributeDefinition.getDescription();
	}

	@Override
	public boolean isRequired() {
		return attributeDefinition.isRequired();
	}

	@Override
	public boolean isMultiValue() {
		return attributeDefinition.isMultiValue();
	}

	@Override
	public ValueType getValueType() {
		return attributeDefinition.getValueType();
	}

	@Override
	public String[] getChoices() {
		return attributeDefinition.getChoices();
	}

}
