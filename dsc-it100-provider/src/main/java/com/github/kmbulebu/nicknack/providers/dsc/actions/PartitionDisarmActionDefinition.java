package com.github.kmbulebu.nicknack.providers.dsc.actions;

import java.util.UUID;

import rx.subjects.PublishSubject;

import com.github.kmbulebu.dsc.it100.commands.write.PartitionDisarmCommand;
import com.github.kmbulebu.dsc.it100.commands.write.WriteCommand;
import com.github.kmbulebu.nicknack.core.actions.Action;
import com.github.kmbulebu.nicknack.core.actions.ActionFailureException;
import com.github.kmbulebu.nicknack.core.actions.ActionParameterException;

public class PartitionDisarmActionDefinition extends DscActionDefinition {
	
	public static final UUID DEF_UUID = UUID.fromString("20f7a865-c08e-4fea-a856-fe31620437d9");

	public PartitionDisarmActionDefinition(PublishSubject<WriteCommand> dscWriteObservable) {
		super(dscWriteObservable, DEF_UUID, "Disarm Partition", 
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
		
		final PartitionDisarmCommand command = new PartitionDisarmCommand(partition, userCode);
		
		super.send(command);
		
	}

}
