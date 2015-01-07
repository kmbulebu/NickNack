package com.github.kmbulebu.nicknack.core.actions;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import com.github.kmbulebu.nicknack.core.attributes.AttributeDefinition;


public abstract class BasicActionDefinition implements ActionDefinition {
	
	private final UUID uuid;
	private final UUID providerUuid;
	private final String name;
	private final List<AttributeDefinition> attributeDefinitions;

	public BasicActionDefinition(UUID uuid, UUID providerUuid, String name, List<AttributeDefinition> parameterDefinitions) {
		super();
		this.uuid = uuid;
		this.providerUuid = providerUuid;
		this.name = name;
		this.attributeDefinitions = parameterDefinitions;
	}
	
	public BasicActionDefinition(UUID uuid, UUID providerUuid, String name, AttributeDefinition... parameterDefinitions) {
		this(uuid, providerUuid, name, Arrays.asList(parameterDefinitions));
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
		return "BasicActionDefinition [uuid=" + uuid + ", providerUuid=" + providerUuid + ", name=" + name
				+ ", parameterDefinitions=" + attributeDefinitions + "]";
	}

	@Override
	public UUID getProviderUUID() {
		return providerUuid;
	}
	
	

}
