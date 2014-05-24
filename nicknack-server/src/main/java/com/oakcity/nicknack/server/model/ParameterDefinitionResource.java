package com.oakcity.nicknack.server.model;

import java.util.UUID;

import org.springframework.hateoas.ResourceSupport;

import com.oakcity.nicknack.core.Unit;
import com.oakcity.nicknack.core.actions.Action.ParameterDefinition;

public class ParameterDefinitionResource extends ResourceSupport implements ParameterDefinition {
		
	public final ParameterDefinition parameterDefinition;
	
	public ParameterDefinitionResource(ParameterDefinition parameterDefinition) {
		this.parameterDefinition = parameterDefinition;
	}

	@Override
	public UUID getUUID() {
		return parameterDefinition.getUUID();
	}

	@Override
	public String getName() {
		return parameterDefinition.getName();
	}

	@Override
	public Unit<?> getUnits() {
		return parameterDefinition.getUnits();
	}

	@Override
	public boolean isRequired() {
		return parameterDefinition.isRequired();
	}

}
