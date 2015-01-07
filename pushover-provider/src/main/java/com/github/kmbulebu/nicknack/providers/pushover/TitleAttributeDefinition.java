package com.github.kmbulebu.nicknack.providers.pushover;

import java.util.UUID;

import com.github.kmbulebu.nicknack.core.attributes.BasicAttributeDefinition;
import com.github.kmbulebu.nicknack.core.units.StringUnit;

public class TitleAttributeDefinition extends BasicAttributeDefinition {

	public static final UUID DEF_UUID = UUID.fromString("1e185e4b-2310-4755-8a49-a4ed8a223000");
	
	public static final TitleAttributeDefinition INSTANCE = new TitleAttributeDefinition();

	public TitleAttributeDefinition() {
		super(DEF_UUID, "Title", StringUnit.INSTANCE, false);
	}
	
}
