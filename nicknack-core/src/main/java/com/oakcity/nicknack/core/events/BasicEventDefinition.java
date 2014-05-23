package com.oakcity.nicknack.core.events;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import com.oakcity.nicknack.core.events.Event.AttributeDefinition;
import com.oakcity.nicknack.core.events.Event.EventDefinition;

public class BasicEventDefinition implements EventDefinition {
	
	private final UUID uuid;
	private final String name;
	private final List<AttributeDefinition> attributeDefinitions;

	public BasicEventDefinition(UUID uuid, String name, List<AttributeDefinition> attributeDefinitions) {
		super();
		this.uuid = uuid;
		this.name = name;
		this.attributeDefinitions = attributeDefinitions;
	}
	
	public BasicEventDefinition(UUID uuid, String name, AttributeDefinition... attributeDefinitions) {
		this(uuid, name, Arrays.asList(attributeDefinitions));
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
	public List<AttributeDefinition> getAttributeDefinitions() {
		return attributeDefinitions;
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
		BasicEventDefinition other = (BasicEventDefinition) obj;
		if (uuid == null) {
			if (other.uuid != null)
				return false;
		} else if (!uuid.equals(other.uuid))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "BasicEventDefinition [uuid=" + uuid + ", name=" + name + ", attributeDefinitions="
				+ attributeDefinitions + "]";
	}
	
	

}
