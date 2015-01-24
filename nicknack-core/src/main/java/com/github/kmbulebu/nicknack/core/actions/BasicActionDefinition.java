package com.github.kmbulebu.nicknack.core.actions;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import com.github.kmbulebu.nicknack.core.attributes.AttributeDefinition;

/**
 * Basic implementation of an ActionDefinition. 
 * 
 * May be extended or used as-is.
 *
 */
public abstract class BasicActionDefinition implements ActionDefinition {
	
	private final UUID uuid;
	private final String name;
	private final String description;
	private final List<AttributeDefinition<?,?>> attributeDefinitions;

	public BasicActionDefinition(UUID uuid, String name, List<AttributeDefinition<?,?>> attributeDefinitions) {
		this(uuid, name, "", attributeDefinitions);
	}
	public BasicActionDefinition(UUID uuid, String name, String description, List<AttributeDefinition<?,?>> attributeDefinitions) {
		super();
		this.uuid = uuid;
		this.name = name;
		this.description = description;
		this.attributeDefinitions = attributeDefinitions;
	}
	
	public BasicActionDefinition(UUID uuid, String name, AttributeDefinition<?,?>... attributeDefinitions) {
		this(uuid, name, "", attributeDefinitions);
	}
	
	public BasicActionDefinition(UUID uuid, String name, String description, AttributeDefinition<?,?>... attributeDefinitions) {
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
	public String getDescription() {
		return description;
	}

	@Override
	public List<AttributeDefinition<?,?>> getAttributeDefinitions() {
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
		BasicActionDefinition other = (BasicActionDefinition) obj;
		if (uuid == null) {
			if (other.uuid != null)
				return false;
		} else if (!uuid.equals(other.uuid))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "BasicActionDefinition [uuid=" + uuid + ", name=" + name + ", description=" + description
				+ ", attributeDefinitions=" + attributeDefinitions + "]";
	}	

}
