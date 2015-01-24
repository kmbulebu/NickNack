package com.github.kmbulebu.nicknack.core.attributes;

import java.util.UUID;

import com.github.kmbulebu.nicknack.core.valuetypes.ValueType;

/**
 * Basic implementation of an AttributeDefinition. 
 * 
 * May be extended or used as-is.
 *
 */
public class BasicAttributeDefinition<T extends ValueType<?>> implements AttributeDefinition<T> {
	
	private final UUID uuid;
	private final String name;
	private final String description;
	private final T valueType;
	private final boolean isRequired;
	
	public BasicAttributeDefinition(UUID uuid, String name, T valueType, boolean isRequired) {
		this(uuid, name, "", valueType, isRequired);
	}
	
	public BasicAttributeDefinition(UUID uuid, String name, String description,  T valueType, boolean isRequired) {
		this.uuid = uuid;
		this.name = name;
		this.description = description;
		this.valueType = valueType;
		this.isRequired = isRequired;
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
	public T getValueType() {
		return valueType;
	}

	@Override
	public boolean isRequired() {
		return isRequired;
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

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BasicAttributeDefinition<?> other = (BasicAttributeDefinition<?>) obj;
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
				+ ", valueType=" + valueType + ", isRequired=" + isRequired + "]";
	}

}
