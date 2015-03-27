package com.github.kmbulebu.nicknack.providers.dsc.actions;

import java.util.UUID;

import com.github.kmbulebu.dsc.it100.commands.write.PartitionDisarmCommand;
import com.github.kmbulebu.nicknack.core.actions.Action;
import com.github.kmbulebu.nicknack.core.actions.ActionAttributeException;
import com.github.kmbulebu.nicknack.core.actions.ActionFailureException;
import com.github.kmbulebu.nicknack.core.actions.BasicActionDefinition;
import com.github.kmbulebu.nicknack.core.providers.Provider;
import com.github.kmbulebu.nicknack.providers.dsc.DscProvider;
import com.github.kmbulebu.nicknack.providers.dsc.attributes.PartitionNumberAttributeDefinition;
import com.github.kmbulebu.nicknack.providers.dsc.attributes.UserCodeAttributeDefinition;

public class PartitionDisarmActionDefinition extends BasicActionDefinition {
	
	public static final UUID DEF_UUID = UUID.fromString("20f7a865-c08e-4fea-a856-fe31620437d9");

	public PartitionDisarmActionDefinition() {
		super(DEF_UUID, "Disarm Partition", 
				PartitionNumberAttributeDefinition.INSTANCE,
				UserCodeAttributeDefinition.INSTANCE);
	}

	@Override
	public void run(Action action, Provider provider) throws ActionFailureException, ActionAttributeException {
		final Integer partition = (Integer) action.getAttributes().get(
				PartitionNumberAttributeDefinition.INSTANCE.getUUID());
		
		final String userCode = (String) action.getAttributes().get(UserCodeAttributeDefinition.INSTANCE.getUUID());

		final PartitionDisarmCommand command = new PartitionDisarmCommand(partition, userCode);
		
		((DscProvider) provider).sendCommand(command);	
		
	}

}
