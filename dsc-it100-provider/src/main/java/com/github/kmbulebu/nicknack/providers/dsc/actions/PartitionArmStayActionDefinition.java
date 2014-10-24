package com.github.kmbulebu.nicknack.providers.dsc.actions;

import java.util.UUID;

import rx.subjects.PublishSubject;

import com.github.kmbulebu.dsc.it100.commands.write.PartitionArmStayCommand;
import com.github.kmbulebu.dsc.it100.commands.write.WriteCommand;
import com.github.kmbulebu.nicknack.core.actions.Action;
import com.github.kmbulebu.nicknack.core.actions.ActionFailureException;
import com.github.kmbulebu.nicknack.core.actions.ActionParameterException;

public class PartitionArmStayActionDefinition extends DscActionDefinition {
	
	public static final UUID DEF_UUID = UUID.fromString("c674d123-9533-4171-8c1f-7cd548d323d5");

	public PartitionArmStayActionDefinition(PublishSubject<WriteCommand> dscWriteObservable) {
		super(dscWriteObservable, DEF_UUID, "Arm Partition in Stay Mode", 
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
		
		final PartitionArmStayCommand command = new PartitionArmStayCommand(partition);
		
		super.send(command);
		
	}

}
