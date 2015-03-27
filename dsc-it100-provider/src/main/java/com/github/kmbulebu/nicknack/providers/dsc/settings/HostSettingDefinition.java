package com.github.kmbulebu.nicknack.providers.dsc.settings;

import java.util.UUID;

import com.github.kmbulebu.nicknack.core.attributes.BasicAttributeDefinition;
import com.github.kmbulebu.nicknack.core.valuetypes.builder.ValueTypeBuilder;
import com.github.kmbulebu.nicknack.core.valuetypes.impl.text.Text;

public class HostSettingDefinition extends BasicAttributeDefinition<Text, String> {
	
	public static final UUID DEF_UUID = UUID.fromString("03d514c6-371b-488a-bc7b-cb1245ac184d");
			
	public HostSettingDefinition() {
		super(DEF_UUID, 
				"Hostname", 
				"Hostname of system with IT-100", 
				ValueTypeBuilder.text().minLength(1).build(),
				null, 
				true,
				false);
	}


}
