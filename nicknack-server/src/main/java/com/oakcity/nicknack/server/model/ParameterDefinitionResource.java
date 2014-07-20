package com.oakcity.nicknack.server.model;

import java.text.ParseException;
import java.util.Collection;
import java.util.UUID;

import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.oakcity.nicknack.core.actions.Action.ParameterDefinition;
import com.oakcity.nicknack.core.units.Unit;

@Relation(value="ParameterDefinition", collectionRelation="ParameterDefinitions")
public class ParameterDefinitionResource extends ResourceSupport implements ParameterDefinition {
		
	@JsonIgnore
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
	public Unit getUnits() {
		return parameterDefinition.getUnits();
	}

	@Override
	public boolean isRequired() {
		return parameterDefinition.isRequired();
	}

	@Override
	public String format(String rawValue) throws ParseException {
		return parameterDefinition.format(rawValue);
	}

	@Override
	public Collection<String> validate(String value) {
		return parameterDefinition.validate(value);
	}

}
