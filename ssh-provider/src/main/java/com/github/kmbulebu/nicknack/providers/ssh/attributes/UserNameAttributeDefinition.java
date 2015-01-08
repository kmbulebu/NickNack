package com.github.kmbulebu.nicknack.providers.ssh.attributes;

import java.util.UUID;

import com.github.kmbulebu.nicknack.core.attributes.BasicAttributeDefinition;
import com.github.kmbulebu.nicknack.core.units.StringUnit;

public class UserNameAttributeDefinition extends BasicAttributeDefinition {

	public static final UUID DEF_UUID = UUID.fromString("a76dfe1e-88fb-485d-9ae8-45b00a6a4b47");
	
	public static final UserNameAttributeDefinition INSTANCE = new UserNameAttributeDefinition();
	
	public UserNameAttributeDefinition() {
		super(DEF_UUID, "Username", StringUnit.INSTANCE, true);
	}

}
