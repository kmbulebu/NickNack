package com.github.kmbulebu.nicknack.providers.dsc.actions;

import java.util.UUID;

import rx.subjects.PublishSubject;

import com.github.kmbulebu.dsc.it100.commands.write.WriteCommand;
import com.github.kmbulebu.nicknack.core.actions.Action;
import com.github.kmbulebu.nicknack.core.actions.ActionFailureException;
import com.github.kmbulebu.nicknack.core.actions.ActionParameterException;
import com.github.kmbulebu.nicknack.core.actions.BasicActionDefinition;
import com.github.kmbulebu.nicknack.core.attributes.AttributeDefinition;

public abstract class AbstractDscActionDefinition extends BasicActionDefinition {
	
	private final PublishSubject<WriteCommand> dscWriteObservable;
	
	public AbstractDscActionDefinition(PublishSubject<WriteCommand> dscWriteObservable, UUID uuid, String name, AttributeDefinition<?,?>... attributeDefinitions) {
		super(uuid, name, attributeDefinitions);
		this.dscWriteObservable = dscWriteObservable;
	}
	
	protected void send(WriteCommand command) {
		dscWriteObservable.onNext(command);
	}
	
	public abstract void run(Action action) throws ActionFailureException, ActionParameterException;

}
