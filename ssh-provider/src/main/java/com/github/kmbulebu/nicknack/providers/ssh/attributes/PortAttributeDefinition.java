package com.github.kmbulebu.nicknack.providers.ssh.attributes;

import java.util.UUID;

import com.github.kmbulebu.nicknack.core.attributes.BasicAttributeDefinition;
import com.github.kmbulebu.nicknack.core.units.IntegerUnit;

public class PortAttributeDefinition extends BasicAttributeDefinition {

	public static final UUID DEF_UUID = UUID.fromString("71105ee0-5711-4907-bffc-32e14c3e78ca");
	
	public static final PortAttributeDefinition INSTANCE = new PortAttributeDefinition();
	
	public PortAttributeDefinition() {
		super(DEF_UUID, "SSH port number", IntegerUnit.INSTANCE, false);
	}	

}
