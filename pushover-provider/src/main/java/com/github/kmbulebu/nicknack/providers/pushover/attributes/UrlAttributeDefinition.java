package com.github.kmbulebu.nicknack.providers.pushover.attributes;

import java.util.UUID;

import com.github.kmbulebu.nicknack.core.attributes.BasicAttributeDefinition;
import com.github.kmbulebu.nicknack.core.units.StringUnit;

public class UrlAttributeDefinition extends BasicAttributeDefinition {

	public static final UUID DEF_UUID = UUID.fromString("52fc270d-7a94-40c5-8793-6049f90944c1");
	
	public static final UrlAttributeDefinition INSTANCE = new UrlAttributeDefinition();

	public UrlAttributeDefinition() {
		super(DEF_UUID, "URL", StringUnit.INSTANCE, false);
	}

}
