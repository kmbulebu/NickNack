package com.github.kmbulebu.nicknack.providers.xbmc.attributes;

import java.util.UUID;

import com.github.kmbulebu.nicknack.core.attributes.BasicAttributeDefinition;
import com.github.kmbulebu.nicknack.core.valuetypes.TextType;

public class HostAttributeDefinition extends BasicAttributeDefinition<TextType> {
	
	public static final UUID DEF_UUID = UUID.fromString("4e1bd491-fd3d-47bf-9910-8cf79b139b1d");
	
	public static final HostAttributeDefinition INSTANCE = new HostAttributeDefinition();

	public HostAttributeDefinition() {
		super(DEF_UUID, "Host", new TextType(), true);
	}

/*	@Override
	public String format(String rawValue) {
		return rawValue;
	}

	@Override
	public Collection<String> validate(String value) {
		if (logger.isTraceEnabled()) {
			logger.entry(value);
		}
		// Dumb validation for now. Will get better when we implement a value chooser.
		if (value == null || value.isEmpty()) {
			return Collections.singleton("A host value must be provided.");
		}
		return Collections.emptyList();
	}
*/
}
