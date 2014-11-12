package com.github.kmbulebu.nicknack.server.actions;

import java.util.UUID;

import com.github.kmbulebu.nicknack.core.actions.BasicActionDefinition;
import com.github.kmbulebu.nicknack.server.NickNackServerProvider;

public class DummyActionDefinition extends BasicActionDefinition {
	
	private static final UUID ACTION_DEF_UUID = UUID.fromString("327fa012-2054-46f3-8bcd-7db55b179524");
	
	public static final DummyActionDefinition INSTANCE = new DummyActionDefinition();

	public DummyActionDefinition() {
		super(ACTION_DEF_UUID, NickNackServerProvider.PROVIDER_UUID, "Dummy Action");
	}

}
