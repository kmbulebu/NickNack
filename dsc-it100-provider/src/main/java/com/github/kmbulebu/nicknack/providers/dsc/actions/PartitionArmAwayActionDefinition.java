package com.github.kmbulebu.nicknack.providers.dsc.actions;

import java.util.UUID;

import com.github.kmbulebu.dsc.it100.commands.write.PartitionArmAwayCommand;
import com.github.kmbulebu.nicknack.core.actions.Action;
import com.github.kmbulebu.nicknack.core.actions.ActionAttributeException;
import com.github.kmbulebu.nicknack.core.actions.ActionFailureException;
import com.github.kmbulebu.nicknack.core.actions.BasicActionDefinition;
import com.github.kmbulebu.nicknack.core.providers.Provider;
import com.github.kmbulebu.nicknack.providers.dsc.DscProvider;
import com.github.kmbulebu.nicknack.providers.dsc.attributes.PartitionNumberAttributeDefinition;

public class PartitionArmAwayActionDefinition extends BasicActionDefinition {
	
	public static final UUID DEF_UUID = UUID.fromString("e41c0c10-1f18-471f-a0ab-888815c54adc");

	public PartitionArmAwayActionDefinition() {
		super(DEF_UUID, "Arm Partition in Away Mode", 
				PartitionNumberAttributeDefinition.INSTANCE);
	}

	@Override
	public void run(Action action, Provider provider)  throws ActionFailureException, ActionAttributeException {
		final Integer partition = (Integer) action.getAttributes().get(
				PartitionNumberAttributeDefinition.INSTANCE.getUUID());
		
		final PartitionArmAwayCommand command = new PartitionArmAwayCommand(partition);
		
		((DscProvider) provider).sendCommand(command);	
	}

}
