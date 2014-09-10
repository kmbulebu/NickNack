package com.oakcity.nicknack.providers.dsc.actions;

import java.util.UUID;

import rx.subjects.PublishSubject;

import com.github.kmbulebu.dsc.it100.commands.write.PartitionArmWithCodeCommand;
import com.github.kmbulebu.dsc.it100.commands.write.WriteCommand;
import com.oakcity.nicknack.core.actions.Action;
import com.oakcity.nicknack.core.actions.ActionFailureException;
import com.oakcity.nicknack.core.actions.ActionParameterException;

public class PartitionArmWithCodeActionDefinition extends DscActionDefinition {
	
	public static final UUID DEF_UUID = UUID.fromString("0a50f971-51bc-4b43-ad18-05dec0e54e59");

	public PartitionArmWithCodeActionDefinition(PublishSubject<WriteCommand> dscWriteObservable) {
		super(dscWriteObservable, DEF_UUID, "Arm Partition in Away Mode with User Code", 
				PartitionNumberParameterDefinition.INSTANCE,
				UserCodeParameterDefinition.INSTANCE);
	}

	@Override
	public void run(Action action) throws ActionFailureException, ActionParameterException {
		String partitionStr = action.getParameters().get(PartitionNumberParameterDefinition.INSTANCE.getUUID());
		if (partitionStr == null) {
			throw new ActionParameterException(PartitionNumberParameterDefinition.INSTANCE.getName() + " is missing.");
		}
		
		String userCode = action.getParameters().get(UserCodeParameterDefinition.INSTANCE.getUUID());
		if (userCode == null) {
			throw new ActionParameterException(UserCodeParameterDefinition.INSTANCE.getName() + " is missing.");
		}
		
		int partition;
		
		try {
			partition = Integer.parseInt(partitionStr);
		} catch (NumberFormatException e) {
			throw new ActionParameterException("Partition parameter must be an integer number.");
		}
		
		final PartitionArmWithCodeCommand command = new PartitionArmWithCodeCommand(partition, userCode);
		
		super.send(command);
		
	}

}
