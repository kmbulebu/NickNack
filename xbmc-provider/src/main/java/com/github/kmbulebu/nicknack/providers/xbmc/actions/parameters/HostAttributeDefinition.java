package com.github.kmbulebu.nicknack.providers.xbmc.actions.parameters;

import java.util.UUID;

import com.github.kmbulebu.nicknack.core.attributes.BasicAttributeDefinition;
import com.github.kmbulebu.nicknack.core.units.StringUnit;

public class HostAttributeDefinition extends BasicAttributeDefinition {
	
	public static final UUID DEF_UUID = UUID.fromString("4e1bd491-fd3d-47bf-9910-8cf79b139b1d");
	
	public static final HostAttributeDefinition INSTANCE = new HostAttributeDefinition();

	public HostAttributeDefinition() {
		super(DEF_UUID, "Host", StringUnit.INSTANCE, true);
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
