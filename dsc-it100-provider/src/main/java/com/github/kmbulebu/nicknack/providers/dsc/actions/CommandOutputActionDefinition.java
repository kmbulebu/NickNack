package com.github.kmbulebu.nicknack.providers.dsc.actions;

import java.util.UUID;

import rx.subjects.PublishSubject;

import com.github.kmbulebu.dsc.it100.commands.write.CommandOutputControl;
import com.github.kmbulebu.dsc.it100.commands.write.WriteCommand;
import com.github.kmbulebu.nicknack.core.actions.Action;
import com.github.kmbulebu.nicknack.core.actions.ActionFailureException;
import com.github.kmbulebu.nicknack.core.actions.ActionAttributeException;
import com.github.kmbulebu.nicknack.providers.dsc.attributes.PartitionNumberAttributeDefinition;
import com.github.kmbulebu.nicknack.providers.dsc.attributes.PgmNumberAttributeDefinition;

public class CommandOutputActionDefinition extends AbstractDscActionDefinition {
	
	public static final UUID DEF_UUID = UUID.fromString("537e6c7f-1476-42e2-8e45-96f839ad5c74");

	public CommandOutputActionDefinition(PublishSubject<WriteCommand> dscWriteObservable) {
		super(dscWriteObservable, DEF_UUID, "Trigger Command Output", 
				PartitionNumberAttributeDefinition.INSTANCE,
				PgmNumberAttributeDefinition.INSTANCE);
	}

	@Override
	public void run(Action action) throws ActionFailureException, ActionAttributeException {
		String partitionStr = action.getAttributes().get(PartitionNumberAttributeDefinition.INSTANCE.getUUID());
		if (partitionStr == null) {
			throw new ActionAttributeException(PartitionNumberAttributeDefinition.INSTANCE.getName() + " is missing.");
		}
		
		String pgmStr = action.getAttributes().get(PgmNumberAttributeDefinition.INSTANCE.getUUID());
		if (pgmStr == null) {
			throw new ActionAttributeException(PgmNumberAttributeDefinition.INSTANCE.getName() + " is missing.");
		}
		
		int pgm;
		int partition;
		
		try {
			pgm = Integer.parseInt(pgmStr);
			partition = Integer.parseInt(partitionStr);
		} catch (NumberFormatException e) {
			throw new ActionAttributeException("Parameters must be integer numbers.");
		}
		
		final CommandOutputControl command = new CommandOutputControl(partition, pgm);
		
		super.send(command);
		
	}

}
