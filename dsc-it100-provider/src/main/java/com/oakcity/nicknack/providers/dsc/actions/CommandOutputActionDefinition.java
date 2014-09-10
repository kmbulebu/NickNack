package com.oakcity.nicknack.providers.dsc.actions;

import java.util.UUID;

import rx.subjects.PublishSubject;

import com.github.kmbulebu.dsc.it100.commands.write.CommandOutputControl;
import com.github.kmbulebu.dsc.it100.commands.write.WriteCommand;
import com.oakcity.nicknack.core.actions.Action;
import com.oakcity.nicknack.core.actions.ActionFailureException;
import com.oakcity.nicknack.core.actions.ActionParameterException;

public class CommandOutputActionDefinition extends DscActionDefinition {
	
	public static final UUID DEF_UUID = UUID.fromString("537e6c7f-1476-42e2-8e45-96f839ad5c74");

	public CommandOutputActionDefinition(PublishSubject<WriteCommand> dscWriteObservable) {
		super(dscWriteObservable, DEF_UUID, "Trigger Command Output", 
				PartitionNumberParameterDefinition.INSTANCE,
				PgmNumberParameterDefinition.INSTANCE);
	}

	@Override
	public void run(Action action) throws ActionFailureException, ActionParameterException {
		String partitionStr = action.getParameters().get(PartitionNumberParameterDefinition.INSTANCE.getUUID());
		if (partitionStr == null) {
			throw new ActionParameterException(PartitionNumberParameterDefinition.INSTANCE.getName() + " is missing.");
		}
		
		String pgmStr = action.getParameters().get(PgmNumberParameterDefinition.INSTANCE.getUUID());
		if (pgmStr == null) {
			throw new ActionParameterException(PgmNumberParameterDefinition.INSTANCE.getName() + " is missing.");
		}
		
		int pgm;
		int partition;
		
		try {
			pgm = Integer.parseInt(pgmStr);
			partition = Integer.parseInt(partitionStr);
		} catch (NumberFormatException e) {
			throw new ActionParameterException("Parameters must be integer numbers.");
		}
		
		final CommandOutputControl command = new CommandOutputControl(partition, pgm);
		
		super.send(command);
		
	}

}
