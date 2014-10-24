package com.github.kmbulebu.nicknack.providers.dsc.actions;

import java.util.UUID;

import rx.subjects.PublishSubject;

import com.github.kmbulebu.dsc.it100.commands.write.WriteCommand;
import com.github.kmbulebu.nicknack.core.actions.Action;
import com.github.kmbulebu.nicknack.core.actions.ActionFailureException;
import com.github.kmbulebu.nicknack.core.actions.ActionParameterException;
import com.github.kmbulebu.nicknack.core.actions.BasicActionDefinition;
import com.github.kmbulebu.nicknack.core.actions.ParameterDefinition;
import com.github.kmbulebu.nicknack.providers.dsc.DscProvider;

public abstract class DscActionDefinition extends BasicActionDefinition {
	
	private final PublishSubject<WriteCommand> dscWriteObservable;
	
	public DscActionDefinition(PublishSubject<WriteCommand> dscWriteObservable, UUID uuid, String name, ParameterDefinition... parameterDefinitions) {
		super(uuid, DscProvider.PROVIDER_UUID, name, parameterDefinitions);
		this.dscWriteObservable = dscWriteObservable;
	}
	
	protected void send(WriteCommand command) {
		dscWriteObservable.onNext(command);
	}
	
	public abstract void run(Action action) throws ActionFailureException, ActionParameterException;

}
