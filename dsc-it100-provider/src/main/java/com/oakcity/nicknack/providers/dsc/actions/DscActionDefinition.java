package com.oakcity.nicknack.providers.dsc.actions;

import java.util.UUID;

import rx.subjects.PublishSubject;

import com.oakcity.dsc.it100.commands.write.WriteCommand;
import com.oakcity.nicknack.core.actions.BasicActionDefinition;
import com.oakcity.nicknack.core.actions.ParameterDefinition;
import com.oakcity.nicknack.providers.dsc.DscProvider;

public abstract class DscActionDefinition extends BasicActionDefinition {
	
	private final PublishSubject<WriteCommand> dscWriteObservable;
	
	public DscActionDefinition(PublishSubject<WriteCommand> dscWriteObservable, UUID uuid, String name, ParameterDefinition... parameterDefinitions) {
		super(uuid, DscProvider.PROVIDER_UUID, name, parameterDefinitions);
		this.dscWriteObservable = dscWriteObservable;
	}
	
	protected void send(WriteCommand command) {
		dscWriteObservable.onNext(command);
	}

}
