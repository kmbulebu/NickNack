package com.oakcity.nicknack.providers.ssh;

import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

import com.oakcity.nicknack.core.actions.parameters.BasicParameterDefinition;
import com.oakcity.nicknack.core.units.StringUnit;

public class HostParameterDefinition extends BasicParameterDefinition<StringUnit> {

	public static final UUID DEF_UUID = UUID.fromString("f0e20833-d1fc-4359-a009-04d835fac390");
	
	public static final HostParameterDefinition INSTANCE = new HostParameterDefinition();

	public HostParameterDefinition() {
		super(DEF_UUID, "Hostname or IP Address", StringUnit.INSTANCE, true);
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
