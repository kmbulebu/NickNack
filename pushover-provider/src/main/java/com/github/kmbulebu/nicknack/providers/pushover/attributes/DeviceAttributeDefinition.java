package com.github.kmbulebu.nicknack.providers.pushover.attributes;

import java.util.UUID;

import com.github.kmbulebu.nicknack.core.attributes.BasicAttributeDefinition;
import com.github.kmbulebu.nicknack.core.valuetypes.TextType;

public class DeviceAttributeDefinition extends BasicAttributeDefinition<TextType> {

	public static final UUID DEF_UUID = UUID.fromString("993d0718-e02e-4224-9214-5c9799763f77");
	
	public static final DeviceAttributeDefinition INSTANCE = new DeviceAttributeDefinition();

	public DeviceAttributeDefinition() {
		super(DEF_UUID, "Device", new TextType(), false);
	}	

}
