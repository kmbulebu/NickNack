package com.oakcity.nicknack.core.actions;

import java.util.UUID;

import com.oakcity.nicknack.core.Unit;
import com.oakcity.nicknack.core.actions.Action.ParameterDefinition;

public class BasicParameterDefinition implements ParameterDefinition {
	
	private final UUID uuid;
	private final String name;
	private final Unit<?> units;
	private final boolean isRequired;
	
	public BasicParameterDefinition(UUID uuid, String name, Unit<?> units, boolean isRequired) {
		super();
		this.uuid = uuid;
		this.name = name;
		this.units = units;
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
	public Unit<?> getUnits() {
		return units;
	}
	
	@Override
	public boolean isRequired() {
		return isRequired;
	}

	
}
