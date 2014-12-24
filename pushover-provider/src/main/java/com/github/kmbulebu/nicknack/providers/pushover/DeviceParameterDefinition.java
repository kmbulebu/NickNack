package com.github.kmbulebu.nicknack.providers.pushover;

import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

import com.github.kmbulebu.nicknack.core.actions.parameters.BasicParameterDefinition;
import com.github.kmbulebu.nicknack.core.units.StringUnit;

public class DeviceParameterDefinition extends BasicParameterDefinition<StringUnit> {

	public static final UUID DEF_UUID = UUID.fromString("993d0718-e02e-4224-9214-5c9799763f77");
	
	public static final DeviceParameterDefinition INSTANCE = new DeviceParameterDefinition();

	public DeviceParameterDefinition() {
		super(DEF_UUID, "Device", StringUnit.INSTANCE, false);
	}

	@Override
	public String format(String rawValue) {
		return rawValue;
	}

	@Override
	public Collection<String> validate(String value) {
		return Collections.emptyList();
	}
	
	

}
