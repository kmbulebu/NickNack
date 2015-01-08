package com.github.kmbulebu.nicknack.providers.ssh.attributes;

import java.util.UUID;

import com.github.kmbulebu.nicknack.core.attributes.BasicAttributeDefinition;
import com.github.kmbulebu.nicknack.core.units.IntegerUnit;

public class SuccessfulReturnCodeAttributeDefinition extends BasicAttributeDefinition {

	public static final UUID DEF_UUID = UUID.fromString("df897502-4ac3-4b88-89c6-f596a4beb5bc");
	
	public static final SuccessfulReturnCodeAttributeDefinition INSTANCE = new SuccessfulReturnCodeAttributeDefinition();
	
	public SuccessfulReturnCodeAttributeDefinition() {
		super(DEF_UUID, "Successful Return Code", IntegerUnit.INSTANCE, false);
	}

}
