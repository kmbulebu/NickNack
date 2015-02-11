package com.github.kmbulebu.nicknack.providers.ssh.attributes;

import java.util.UUID;

import com.github.kmbulebu.nicknack.core.attributes.BasicAttributeDefinition;
import com.github.kmbulebu.nicknack.core.valuetypes.TextType;

public class CommandLineAttributeDefinition extends BasicAttributeDefinition<TextType> {

	public static final UUID DEF_UUID = UUID.fromString("8a2afea6-36a3-4058-a615-194ba7660382");
	
	public static final CommandLineAttributeDefinition INSTANCE = new CommandLineAttributeDefinition();

	public CommandLineAttributeDefinition() {
		super(DEF_UUID, "Command Line", new TextType(), true);
	}

}
