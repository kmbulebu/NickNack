package com.oakcity.nicknack.core.actions;

import java.util.UUID;

import com.oakcity.nicknack.core.Unit;
import com.oakcity.nicknack.core.actions.Action.ParameterDefinition;

public class BasicParameterDefinition<T extends Unit<?>> implements ParameterDefinition {

	private final UUID uuid;
	private final String name;
	private final T units;
	private final boolean isRequired;

	public BasicParameterDefinition(UUID uuid, String name, T units, boolean isRequired) {
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
	public T getUnits() {
		return units;
	}

	@Override
	public boolean isRequired() {
		return isRequired;
	}

}
