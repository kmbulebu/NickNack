package com.github.kmbulebu.nicknack.providers.dsc.actions;

import java.util.UUID;

import com.github.kmbulebu.dsc.it100.commands.write.PartitionArmWithCodeCommand;
import com.github.kmbulebu.nicknack.core.actions.Action;
import com.github.kmbulebu.nicknack.core.actions.ActionAttributeException;
import com.github.kmbulebu.nicknack.core.actions.ActionFailureException;
import com.github.kmbulebu.nicknack.core.actions.BasicActionDefinition;
import com.github.kmbulebu.nicknack.core.providers.Provider;
import com.github.kmbulebu.nicknack.providers.dsc.DscProvider;
import com.github.kmbulebu.nicknack.providers.dsc.attributes.PartitionNumberAttributeDefinition;
import com.github.kmbulebu.nicknack.providers.dsc.attributes.UserCodeAttributeDefinition;

public class PartitionArmWithCodeActionDefinition extends BasicActionDefinition {
	
	public static final UUID DEF_UUID = UUID.fromString("0a50f971-51bc-4b43-ad18-05dec0e54e59");

	public PartitionArmWithCodeActionDefinition() {
		super(DEF_UUID, "Arm Partition in Away Mode with User Code", 
				PartitionNumberAttributeDefinition.INSTANCE,
				UserCodeAttributeDefinition.INSTANCE);
	}

	@Override
	public void run(Action action, Provider provider) throws ActionFailureException, ActionAttributeException {
		final Integer partition = (Integer) action.getAttributes().get(
				PartitionNumberAttributeDefinition.INSTANCE.getUUID());
		
		final String userCode = (String) action.getAttributes().get(UserCodeAttributeDefinition.INSTANCE.getUUID());

		
		final PartitionArmWithCodeCommand command = new PartitionArmWithCodeCommand(partition, userCode);
		
		((DscProvider) provider).sendCommand(command);	
		
	}

}
