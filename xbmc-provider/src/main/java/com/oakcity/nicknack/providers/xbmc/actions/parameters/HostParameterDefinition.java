package com.oakcity.nicknack.providers.xbmc.actions.parameters;

import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

import com.oakcity.nicknack.core.actions.parameters.BasicParameterDefinition;
import com.oakcity.nicknack.core.units.StringUnit;

public class HostParameterDefinition extends BasicParameterDefinition<StringUnit> {
	
	public static final UUID DEF_UUID = UUID.fromString("4e1bd491-fd3d-47bf-9910-8cf79b139b1d");
	
	public static final HostParameterDefinition INSTANCE = new HostParameterDefinition();

	public HostParameterDefinition() {
		super(DEF_UUID, "Host", StringUnit.INSTANCE, true);
	}

	@Override
	public String format(String rawValue) {
		return rawValue;
	}

	@Override
	public Collection<String> validate(String value) {
		// Dumb validation for now. Will get better when we implement a value chooser.
		if (value == null || value.isEmpty()) {
			return Collections.singleton("A host value must be provided.");
		}
		return Collections.emptyList();
	}

}
