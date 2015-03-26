package com.github.kmbulebu.nicknack.providers.dsc.actions;

import java.util.UUID;

import rx.subjects.PublishSubject;

import com.github.kmbulebu.dsc.it100.commands.write.PartitionDisarmCommand;
import com.github.kmbulebu.dsc.it100.commands.write.WriteCommand;
import com.github.kmbulebu.nicknack.core.actions.Action;
import com.github.kmbulebu.nicknack.core.actions.ActionFailureException;
import com.github.kmbulebu.nicknack.core.actions.ActionAttributeException;
import com.github.kmbulebu.nicknack.providers.dsc.attributes.PartitionNumberAttributeDefinition;
import com.github.kmbulebu.nicknack.providers.dsc.attributes.UserCodeAttributeDefinition;

public class PartitionDisarmActionDefinition extends AbstractDscActionDefinition {
	
	public static final UUID DEF_UUID = UUID.fromString("20f7a865-c08e-4fea-a856-fe31620437d9");

	public PartitionDisarmActionDefinition(PublishSubject<WriteCommand> dscWriteObservable) {
		super(dscWriteObservable, DEF_UUID, "Disarm Partition", 
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
		
		final PartitionDisarmCommand command = new PartitionDisarmCommand(partition, userCode);
		
		super.send(command);
		
	}

}
