package com.github.kmbulebu.nicknack.providers.dsc.settings;

import java.util.UUID;

import com.github.kmbulebu.nicknack.core.attributes.BasicAttributeDefinition;
import com.github.kmbulebu.nicknack.core.valuetypes.builder.ValueTypeBuilder;
import com.github.kmbulebu.nicknack.core.valuetypes.impl.wholenumber.WholeNumber;

public class PortSettingDefinition extends BasicAttributeDefinition<WholeNumber, Integer>  {
	
	public static final UUID DEF_UUID = UUID.fromString("6b6670cf-677a-4454-9b8f-a99cd2b21bdb");

	public PortSettingDefinition() {
		super(DEF_UUID, 
				"Port Number", 
				"Port number of machine with IT-100",
				ValueTypeBuilder.wholeNumber().min(1).max(65536).build(),
				null, 
				true,
				false);
	}

}
