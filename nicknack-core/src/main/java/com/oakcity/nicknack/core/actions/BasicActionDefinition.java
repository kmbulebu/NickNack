package com.oakcity.nicknack.core.actions;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import com.oakcity.nicknack.core.actions.Action.ActionDefinition;
import com.oakcity.nicknack.core.actions.Action.ParameterDefinition;


public class BasicActionDefinition implements ActionDefinition {
	
	private final UUID uuid;
	private final UUID providerUuid;
	private final String name;
	private final List<ParameterDefinition> parameterDefinitions;

	public BasicActionDefinition(UUID uuid, UUID providerUuid, String name, List<ParameterDefinition> parameterDefinitions) {
		super();
		this.uuid = uuid;
		this.providerUuid = providerUuid;
		this.name = name;
		this.parameterDefinitions = parameterDefinitions;
	}
	
	public BasicActionDefinition(UUID uuid, UUID providerUuid, String name, ParameterDefinition... parameterDefinitions) {
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
	public List<ParameterDefinition> getParameterDefinitions() {
		return parameterDefinitions;
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
				+ ", parameterDefinitions=" + parameterDefinitions + "]";
	}

	@Override
	public UUID getProviderUUID() {
		return providerUuid;
	}
	
	

}
