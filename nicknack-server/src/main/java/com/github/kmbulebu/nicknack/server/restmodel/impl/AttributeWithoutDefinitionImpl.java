package com.github.kmbulebu.nicknack.server.restmodel.impl;

import java.util.UUID;

import com.github.kmbulebu.nicknack.server.restmodel.Attribute;

public class AttributeWithoutDefinitionImpl implements Attribute {

	private UUID uuid;
	private String[] values;

	@Override
	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	@Override
	public String getName() {
		return null;
	}

	@Override
	public String getDescription() {
		return null;
	}

	@Override
	public boolean isRequired() {
		return false;
	}

	@Override
	public boolean isMultiValue() {
		return false;
	}
	
	@Override
	public ValueType getValueType() {
		return null;
	}

	@Override
	public String[] getChoices() {
		return null;
	}

	@Override
	public String[] getValues() {
		return this.values;
	}
	
	public void setValues(String[] values) {
		this.values = values;
	}


}
