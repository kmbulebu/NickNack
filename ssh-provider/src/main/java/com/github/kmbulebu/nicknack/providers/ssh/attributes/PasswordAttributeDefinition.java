package com.github.kmbulebu.nicknack.providers.ssh.attributes;

import java.util.UUID;

import com.github.kmbulebu.nicknack.core.attributes.BasicAttributeDefinition;
import com.github.kmbulebu.nicknack.core.valuetypes.TextType;

public class PasswordAttributeDefinition extends BasicAttributeDefinition<TextType> {

	public static final UUID DEF_UUID = UUID.fromString("41493b1a-a270-4bbb-befa-2cf286852637");
	
	public static final PasswordAttributeDefinition INSTANCE = new PasswordAttributeDefinition();
	
	public PasswordAttributeDefinition() {
		super(DEF_UUID, "Password", new TextType(), true);
	}

}
