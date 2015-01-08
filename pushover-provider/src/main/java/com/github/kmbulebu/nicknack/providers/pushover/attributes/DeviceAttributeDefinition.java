package com.github.kmbulebu.nicknack.providers.pushover.attributes;

import java.util.UUID;

import com.github.kmbulebu.nicknack.core.attributes.BasicAttributeDefinition;
import com.github.kmbulebu.nicknack.core.units.StringUnit;

public class DeviceAttributeDefinition extends BasicAttributeDefinition {

	public static final UUID DEF_UUID = UUID.fromString("993d0718-e02e-4224-9214-5c9799763f77");
	
	public static final DeviceAttributeDefinition INSTANCE = new DeviceAttributeDefinition();

	public DeviceAttributeDefinition() {
		super(DEF_UUID, "Device", StringUnit.INSTANCE, false);
	}	

}
