package com.oakcity.nicknack.providers.xbmc.actions.parameters;

import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

import com.oakcity.nicknack.core.actions.parameters.BasicParameterDefinition;
import com.oakcity.nicknack.core.units.StringUnit;

public class TitleParameterDefinition extends BasicParameterDefinition<StringUnit> {
	
	public static final UUID DEF_UUID = UUID.fromString("a43e0ab3-3bb4-4a5d-9b13-cdd9f8d20279");
	
	public static final TitleParameterDefinition INSTANCE = new TitleParameterDefinition();

	public TitleParameterDefinition() {
		super(DEF_UUID, "Title", StringUnit.INSTANCE, true);
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
