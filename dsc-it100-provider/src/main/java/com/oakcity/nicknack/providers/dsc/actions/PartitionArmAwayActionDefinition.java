package com.oakcity.nicknack.providers.dsc.actions;

import java.util.UUID;

import rx.subjects.PublishSubject;

import com.oakcity.dsc.it100.commands.write.PartitionArmAwayCommand;
import com.oakcity.dsc.it100.commands.write.WriteCommand;
import com.oakcity.nicknack.core.actions.Action;
import com.oakcity.nicknack.core.actions.ActionFailureException;
import com.oakcity.nicknack.core.actions.ActionParameterException;

public class PartitionArmAwayActionDefinition extends DscActionDefinition {
	
	public static final UUID DEF_UUID = UUID.fromString("e41c0c10-1f18-471f-a0ab-888815c54adc");

	public PartitionArmAwayActionDefinition(PublishSubject<WriteCommand> dscWriteObservable) {
		super(dscWriteObservable, DEF_UUID, "Arm Partition in Away Mode", 
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
		
		final PartitionArmAwayCommand command = new PartitionArmAwayCommand(partition);
		
		super.send(command);
		
	}

}
