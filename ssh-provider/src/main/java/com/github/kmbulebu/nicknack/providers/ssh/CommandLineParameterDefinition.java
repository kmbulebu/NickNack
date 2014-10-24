package com.github.kmbulebu.nicknack.providers.ssh;

import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

import com.github.kmbulebu.nicknack.core.actions.parameters.BasicParameterDefinition;
import com.github.kmbulebu.nicknack.core.units.StringUnit;

public class CommandLineParameterDefinition extends BasicParameterDefinition<StringUnit> {

	public static final UUID DEF_UUID = UUID.fromString("8a2afea6-36a3-4058-a615-194ba7660382");
	
	public static final CommandLineParameterDefinition INSTANCE = new CommandLineParameterDefinition();

	public CommandLineParameterDefinition() {
		super(DEF_UUID, "Command Line", StringUnit.INSTANCE, true);
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
