package com.github.kmbulebu.nicknack.core.attributes;

import java.util.UUID;

import com.github.kmbulebu.nicknack.core.valuetypes.ValueChoices;
import com.github.kmbulebu.nicknack.core.valuetypes.ValueType;

/**
 * Basic implementation of an AttributeDefinition. 
 * 
 * May be extended or used as-is.
 *
 */
public class BasicAttributeDefinition<T extends ValueType<U>, U> implements AttributeDefinition<T, U> {
	
	private final UUID uuid;
	private final String name;
	private final String description;
	private final boolean isRequired;
	private final boolean isMultiValue;
	private final T valueType;
	private final ValueChoices<U> valueChoices;
	
	public BasicAttributeDefinition(UUID uuid, String name, T valueType, ValueChoices<U> valueChoices, boolean isRequired, boolean isMultiValue) {
		this(uuid, name, "", valueType, valueChoices, isRequired, isMultiValue);
	}
	
	public BasicAttributeDefinition(UUID uuid, String name, String description,  T valueType, ValueChoices<U> valueChoices, boolean isRequired, boolean isMultiValue) {
		this.uuid = uuid;
		this.name = name;
		this.valueType = valueType;
		this.valueChoices = valueChoices;
		this.description = description;
		this.isRequired = isRequired;
		this.isMultiValue = isMultiValue;
	}

	@Override
	public UUID getUUID() {
		return uuid;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public boolean isRequired() {
		return isRequired;
	}
	
	@Override
	public boolean isMultiValue() {
		return isMultiValue;
	};
	
	@Override
	public T getValueType() {
		return valueType;
	}
	
	@Override
	public ValueChoices<U> getValueChoices() {
		return valueChoices;
	}
	
	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((uuid == null) ? 0 : uuid.hashCode());
		return result;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BasicAttributeDefinition other = (BasicAttributeDefinition) obj;
		if (uuid == null) {
			if (other.uuid != null)
				return false;
		} else if (!uuid.equals(other.uuid))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "BasicAttributeDefinition [uuid=" + uuid + ", name=" + name + ", description=" + description
				+ ", isRequired=" + isRequired + ", isMultiValue=" + isMultiValue + ", valueType=" + valueType
				+ ", valueChoices=" + valueChoices + "]";
	}



}
