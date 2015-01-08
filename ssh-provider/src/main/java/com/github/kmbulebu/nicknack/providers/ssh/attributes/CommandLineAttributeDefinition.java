package com.github.kmbulebu.nicknack.providers.ssh.attributes;

import java.util.UUID;

import com.github.kmbulebu.nicknack.core.attributes.BasicAttributeDefinition;
import com.github.kmbulebu.nicknack.core.units.StringUnit;

public class CommandLineAttributeDefinition extends BasicAttributeDefinition {

	public static final UUID DEF_UUID = UUID.fromString("8a2afea6-36a3-4058-a615-194ba7660382");
	
	public static final CommandLineAttributeDefinition INSTANCE = new CommandLineAttributeDefinition();

	public CommandLineAttributeDefinition() {
		super(DEF_UUID, "Command Line", StringUnit.INSTANCE, true);
	}

}
