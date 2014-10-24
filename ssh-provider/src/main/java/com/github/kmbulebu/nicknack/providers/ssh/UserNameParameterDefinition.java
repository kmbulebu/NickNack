package com.github.kmbulebu.nicknack.providers.ssh;

import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

import com.github.kmbulebu.nicknack.core.actions.parameters.BasicParameterDefinition;
import com.github.kmbulebu.nicknack.core.units.StringUnit;

public class UserNameParameterDefinition extends BasicParameterDefinition<StringUnit> {

	public static final UUID DEF_UUID = UUID.fromString("a76dfe1e-88fb-485d-9ae8-45b00a6a4b47");
	
	public static final UserNameParameterDefinition INSTANCE = new UserNameParameterDefinition();
	
	public UserNameParameterDefinition() {
		super(DEF_UUID, "Username", StringUnit.INSTANCE, true);
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
