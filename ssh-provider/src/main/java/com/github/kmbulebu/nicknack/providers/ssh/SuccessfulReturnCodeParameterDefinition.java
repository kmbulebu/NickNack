package com.github.kmbulebu.nicknack.providers.ssh;

import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

import com.github.kmbulebu.nicknack.core.actions.parameters.BasicParameterDefinition;
import com.github.kmbulebu.nicknack.core.units.IntegerUnit;

public class SuccessfulReturnCodeParameterDefinition extends BasicParameterDefinition<IntegerUnit> {

	public static final UUID DEF_UUID = UUID.fromString("df897502-4ac3-4b88-89c6-f596a4beb5bc");
	
	public static final SuccessfulReturnCodeParameterDefinition INSTANCE = new SuccessfulReturnCodeParameterDefinition();
	
	public SuccessfulReturnCodeParameterDefinition() {
		super(DEF_UUID, "Successful Return Code", IntegerUnit.INSTANCE, false);
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
