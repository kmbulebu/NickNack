package com.github.kmbulebu.nicknack.providers.ssh.attributes;

import java.util.UUID;

import com.github.kmbulebu.nicknack.core.attributes.BasicAttributeDefinition;
import com.github.kmbulebu.nicknack.core.valuetypes.TextType;

public class HostAttributeDefinition extends BasicAttributeDefinition<TextType> {

	public static final UUID DEF_UUID = UUID.fromString("f0e20833-d1fc-4359-a009-04d835fac390");
	
	public static final HostAttributeDefinition INSTANCE = new HostAttributeDefinition();

	public HostAttributeDefinition() {
		super(DEF_UUID, "Hostname or IP Address", new TextType(), true);
	}

}
