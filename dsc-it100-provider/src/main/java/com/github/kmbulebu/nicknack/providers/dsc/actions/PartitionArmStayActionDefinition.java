package com.github.kmbulebu.nicknack.providers.dsc.actions;

import java.util.UUID;

import com.github.kmbulebu.dsc.it100.commands.write.PartitionArmStayCommand;
import com.github.kmbulebu.nicknack.core.actions.Action;
import com.github.kmbulebu.nicknack.core.actions.ActionAttributeException;
import com.github.kmbulebu.nicknack.core.actions.ActionFailureException;
import com.github.kmbulebu.nicknack.core.actions.BasicActionDefinition;
import com.github.kmbulebu.nicknack.core.providers.Provider;
import com.github.kmbulebu.nicknack.providers.dsc.DscProvider;
import com.github.kmbulebu.nicknack.providers.dsc.attributes.PartitionNumberAttributeDefinition;

public class PartitionArmStayActionDefinition extends BasicActionDefinition {
	
	public static final UUID DEF_UUID = UUID.fromString("c674d123-9533-4171-8c1f-7cd548d323d5");

	public PartitionArmStayActionDefinition() {
		super(DEF_UUID, "Arm Partition in Stay Mode", 
				PartitionNumberAttributeDefinition.INSTANCE);
	}

	@Override
	public void run(Action action, Provider provider) throws ActionFailureException, ActionAttributeException {
		final Integer partition = (Integer) action.getAttributes().get(
				PartitionNumberAttributeDefinition.INSTANCE.getUUID());
		
		final PartitionArmStayCommand command = new PartitionArmStayCommand(partition);
		
		((DscProvider) provider).sendCommand(command);	
		
	}

}
