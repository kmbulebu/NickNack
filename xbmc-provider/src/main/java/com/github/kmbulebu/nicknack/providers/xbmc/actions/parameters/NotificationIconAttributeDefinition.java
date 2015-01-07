package com.github.kmbulebu.nicknack.providers.xbmc.actions.parameters;

import java.util.UUID;

import com.github.kmbulebu.nicknack.core.attributes.BasicAttributeDefinition;
import com.github.kmbulebu.nicknack.core.units.StringUnit;

public class NotificationIconAttributeDefinition extends BasicAttributeDefinition {
	
	public static final UUID DEF_UUID = UUID.fromString("7ef09391-4239-4f4c-913b-05306171a100");
	
	public static final NotificationIconAttributeDefinition INSTANCE = new NotificationIconAttributeDefinition();

	public NotificationIconAttributeDefinition() {
		super(DEF_UUID, "Icon", StringUnit.INSTANCE, true);
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
		if (value == null || value.isEmpty()) {
			return Collections.singleton("Parameter value must not be empty.");
		}
		boolean match = false;
		for (String goodValue : values) {
			if (goodValue.equals(value)) {
				match = true;
				break;
			}
		}
		
		if (!match) {
			return Collections.singleton("Paramater value must be one of " + Arrays.toString(values));
		}
 		return Collections.emptyList();
	}*/
	
	public static final String[] values = {"info","warning", "error"};

}
