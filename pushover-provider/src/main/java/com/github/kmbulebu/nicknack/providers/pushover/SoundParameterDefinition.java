package com.github.kmbulebu.nicknack.providers.pushover;

import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

import com.github.kmbulebu.nicknack.core.actions.parameters.BasicParameterDefinition;
import com.github.kmbulebu.nicknack.core.units.StringUnit;

public class SoundParameterDefinition extends BasicParameterDefinition<StringUnit> {

	public static final UUID DEF_UUID = UUID.fromString("5275c03d-313a-4835-a13d-1d0085c1b6f1");
	
	public static final SoundParameterDefinition INSTANCE = new SoundParameterDefinition();

	public SoundParameterDefinition() {
		super(DEF_UUID, "Sound", StringUnit.INSTANCE, false);
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
