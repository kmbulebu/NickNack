package com.github.kmbulebu.nicknack.providers.dsc.actions;

import java.util.UUID;

import com.github.kmbulebu.dsc.it100.commands.write.CommandOutputControl;
import com.github.kmbulebu.nicknack.core.actions.Action;
import com.github.kmbulebu.nicknack.core.actions.ActionAttributeException;
import com.github.kmbulebu.nicknack.core.actions.ActionFailureException;
import com.github.kmbulebu.nicknack.core.actions.BasicActionDefinition;
import com.github.kmbulebu.nicknack.core.providers.Provider;
import com.github.kmbulebu.nicknack.providers.dsc.DscProvider;
import com.github.kmbulebu.nicknack.providers.dsc.attributes.PartitionNumberAttributeDefinition;
import com.github.kmbulebu.nicknack.providers.dsc.attributes.PgmNumberAttributeDefinition;

public class CommandOutputActionDefinition extends BasicActionDefinition {

	public static final UUID DEF_UUID = UUID.fromString("537e6c7f-1476-42e2-8e45-96f839ad5c74");

	public CommandOutputActionDefinition() {
		super(DEF_UUID, "Trigger Command Output", PartitionNumberAttributeDefinition.INSTANCE,
				PgmNumberAttributeDefinition.INSTANCE);
	}

	public void run(Action action, Provider provider) throws ActionFailureException, ActionAttributeException {
		final Integer partition = (Integer) action.getAttributes().get(
				PartitionNumberAttributeDefinition.INSTANCE.getUUID());

		final Integer pgm = (Integer) action.getAttributes().get(PgmNumberAttributeDefinition.INSTANCE.getUUID());

		final CommandOutputControl command = new CommandOutputControl(partition, pgm);

		((DscProvider) provider).sendCommand(command);
	}

}
