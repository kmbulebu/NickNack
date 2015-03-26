package com.github.kmbulebu.nicknack.providers.dsc.actions;

import java.util.UUID;

import rx.subjects.PublishSubject;

import com.github.kmbulebu.dsc.it100.commands.write.PartitionArmWithCodeCommand;
import com.github.kmbulebu.dsc.it100.commands.write.WriteCommand;
import com.github.kmbulebu.nicknack.core.actions.Action;
import com.github.kmbulebu.nicknack.core.actions.ActionFailureException;
import com.github.kmbulebu.nicknack.core.actions.ActionAttributeException;
import com.github.kmbulebu.nicknack.providers.dsc.attributes.PartitionNumberAttributeDefinition;
import com.github.kmbulebu.nicknack.providers.dsc.attributes.UserCodeAttributeDefinition;

public class PartitionArmWithCodeActionDefinition extends AbstractDscActionDefinition {
	
	public static final UUID DEF_UUID = UUID.fromString("0a50f971-51bc-4b43-ad18-05dec0e54e59");

	public PartitionArmWithCodeActionDefinition(PublishSubject<WriteCommand> dscWriteObservable) {
		super(dscWriteObservable, DEF_UUID, "Arm Partition in Away Mode with User Code", 
				PartitionNumberAttributeDefinition.INSTANCE,
				UserCodeAttributeDefinition.INSTANCE);
	}

	@Override
	public void run(Action action) throws ActionFailureException, ActionAttributeException {
		String partitionStr = action.getAttributes().get(PartitionNumberAttributeDefinition.INSTANCE.getUUID());
		if (partitionStr == null) {
			throw new ActionAttributeException(PartitionNumberAttributeDefinition.INSTANCE.getName() + " is missing.");
		}
		
		String userCode = action.getAttributes().get(UserCodeAttributeDefinition.INSTANCE.getUUID());
		if (userCode == null) {
			throw new ActionAttributeException(UserCodeAttributeDefinition.INSTANCE.getName() + " is missing.");
		}
		
		int partition;
		
		try {
			partition = Integer.parseInt(partitionStr);
		} catch (NumberFormatException e) {
			throw new ActionAttributeException("Partition parameter must be an integer number.");
		}
		
		final PartitionArmWithCodeCommand command = new PartitionArmWithCodeCommand(partition, userCode);
		
		super.send(command);
		
	}

}
