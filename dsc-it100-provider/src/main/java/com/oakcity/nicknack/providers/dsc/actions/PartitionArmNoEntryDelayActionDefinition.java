package com.oakcity.nicknack.providers.dsc.actions;

import java.util.UUID;

import rx.subjects.PublishSubject;

import com.oakcity.dsc.it100.commands.write.PartitionArmedNoEntryDelayCommand;
import com.oakcity.dsc.it100.commands.write.WriteCommand;
import com.oakcity.nicknack.core.actions.Action;
import com.oakcity.nicknack.core.actions.ActionFailureException;
import com.oakcity.nicknack.core.actions.ActionParameterException;

public class PartitionArmNoEntryDelayActionDefinition extends DscActionDefinition {
	
	public static final UUID DEF_UUID = UUID.fromString("656ceded-6f4f-4a72-9991-0a38bab002d7");

	public PartitionArmNoEntryDelayActionDefinition(PublishSubject<WriteCommand> dscWriteObservable) {
		super(dscWriteObservable, DEF_UUID, "Arm Partition without Entry Delay", 
				PartitionNumberParameterDefinition.INSTANCE);
	}

	@Override
	public void run(Action action) throws ActionFailureException, ActionParameterException {
		String partitionStr = action.getParameters().get(PartitionNumberParameterDefinition.INSTANCE.getUUID());
		if (partitionStr == null) {
			throw new ActionParameterException(PartitionNumberParameterDefinition.INSTANCE.getName() + " is missing.");
		}
		
		int partition;
		
		try {
			partition = Integer.parseInt(partitionStr);
		} catch (NumberFormatException e) {
			throw new ActionParameterException("Partition parameter must be an integer number.");
		}
		
		final PartitionArmedNoEntryDelayCommand command = new PartitionArmedNoEntryDelayCommand(partition);
		
		super.send(command);
		
	}

}
