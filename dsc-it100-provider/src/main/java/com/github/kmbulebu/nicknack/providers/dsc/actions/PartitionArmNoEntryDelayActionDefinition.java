package com.github.kmbulebu.nicknack.providers.dsc.actions;

import java.util.UUID;

import com.github.kmbulebu.dsc.it100.commands.write.PartitionArmedNoEntryDelayCommand;
import com.github.kmbulebu.nicknack.core.actions.Action;
import com.github.kmbulebu.nicknack.core.actions.ActionAttributeException;
import com.github.kmbulebu.nicknack.core.actions.ActionFailureException;
import com.github.kmbulebu.nicknack.core.actions.BasicActionDefinition;
import com.github.kmbulebu.nicknack.core.providers.Provider;
import com.github.kmbulebu.nicknack.providers.dsc.DscProvider;
import com.github.kmbulebu.nicknack.providers.dsc.attributes.PartitionNumberAttributeDefinition;

public class PartitionArmNoEntryDelayActionDefinition extends BasicActionDefinition {
	
	public static final UUID DEF_UUID = UUID.fromString("656ceded-6f4f-4a72-9991-0a38bab002d7");

	public PartitionArmNoEntryDelayActionDefinition() {
		super(DEF_UUID, "Arm Partition without Entry Delay", 
				PartitionNumberAttributeDefinition.INSTANCE);
	}

	@Override
	public void run(Action action, Provider provider) throws ActionFailureException, ActionAttributeException {
		final Integer partition = (Integer) action.getAttributes().get(
				PartitionNumberAttributeDefinition.INSTANCE.getUUID());
		
		final PartitionArmedNoEntryDelayCommand command = new PartitionArmedNoEntryDelayCommand(partition);
		
		((DscProvider) provider).sendCommand(command);	
		
	}

}
