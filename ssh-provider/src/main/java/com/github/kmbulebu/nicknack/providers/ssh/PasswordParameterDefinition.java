package com.github.kmbulebu.nicknack.providers.ssh;

import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

import com.github.kmbulebu.nicknack.core.actions.parameters.BasicParameterDefinition;
import com.github.kmbulebu.nicknack.core.units.StringUnit;

public class PasswordParameterDefinition extends BasicParameterDefinition<StringUnit> {

	public static final UUID DEF_UUID = UUID.fromString("41493b1a-a270-4bbb-befa-2cf286852637");
	
	public static final PasswordParameterDefinition INSTANCE = new PasswordParameterDefinition();
	
	public PasswordParameterDefinition() {
		super(DEF_UUID, "Password", StringUnit.INSTANCE, true);
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
