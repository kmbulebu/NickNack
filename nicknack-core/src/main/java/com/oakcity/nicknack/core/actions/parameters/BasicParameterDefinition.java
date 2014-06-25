package com.oakcity.nicknack.core.actions.parameters;

import java.util.Collection;
import java.util.UUID;

import com.oakcity.nicknack.core.actions.Action.ParameterDefinition;
import com.oakcity.nicknack.core.units.Unit;

// TODO Make this an abstract class
public abstract class BasicParameterDefinition<T extends Unit> implements ParameterDefinition {

	private final UUID uuid;
	private final String name;
	private final Unit units;
	private final boolean isRequired;

	public BasicParameterDefinition(UUID uuid, String name, Unit units, boolean isRequired) {
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
	public Unit getUnits() {
		return units;
	}

	@Override
	public boolean isRequired() {
		return isRequired;
	}

	@Override
	public abstract String format(String rawValue);

	@Override
	public abstract Collection<String> validate(String value);

}
