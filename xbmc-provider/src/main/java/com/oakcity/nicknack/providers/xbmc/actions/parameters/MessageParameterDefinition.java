package com.oakcity.nicknack.providers.xbmc.actions.parameters;

import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

import com.oakcity.nicknack.core.actions.parameters.BasicParameterDefinition;
import com.oakcity.nicknack.core.units.StringUnit;

public class MessageParameterDefinition extends BasicParameterDefinition<StringUnit> {
	
	public static final UUID DEF_UUID = UUID.fromString("b505ad85-1261-4d29-999b-4120dfb451ca");
	
	public static final MessageParameterDefinition INSTANCE = new MessageParameterDefinition();

	public MessageParameterDefinition() {
		super(DEF_UUID, "Message", StringUnit.INSTANCE, true);
	}

	@Override
	public String format(String rawValue) {
		return rawValue;
	}

	@Override
	public Collection<String> validate(String value) {
		return Collections.emptyList();
	}

}
