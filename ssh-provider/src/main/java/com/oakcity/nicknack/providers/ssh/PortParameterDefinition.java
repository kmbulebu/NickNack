package com.oakcity.nicknack.providers.ssh;

import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

import com.oakcity.nicknack.core.actions.parameters.BasicParameterDefinition;
import com.oakcity.nicknack.core.units.IntegerUnit;

public class PortParameterDefinition extends BasicParameterDefinition<IntegerUnit> {

	public static final UUID DEF_UUID = UUID.fromString("71105ee0-5711-4907-bffc-32e14c3e78ca");
	
	public static final PortParameterDefinition INSTANCE = new PortParameterDefinition();
	
	public PortParameterDefinition() {
		super(DEF_UUID, "SSH port number", IntegerUnit.INSTANCE, false);
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
