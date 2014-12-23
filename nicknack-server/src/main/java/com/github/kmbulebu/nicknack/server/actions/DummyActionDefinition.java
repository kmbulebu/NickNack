package com.github.kmbulebu.nicknack.server.actions;

import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

import com.github.kmbulebu.nicknack.core.actions.BasicActionDefinition;
import com.github.kmbulebu.nicknack.core.actions.parameters.BasicParameterDefinition;
import com.github.kmbulebu.nicknack.core.units.StringUnit;
import com.github.kmbulebu.nicknack.server.services.impl.NickNackServerProviderImpl;

public class DummyActionDefinition extends BasicActionDefinition {
	
	private static final UUID ACTION_DEF_UUID = UUID.fromString("327fa012-2054-46f3-8bcd-7db55b179524");
	
	public static final DummyActionDefinition INSTANCE = new DummyActionDefinition();

	public DummyActionDefinition() {
		super(ACTION_DEF_UUID, NickNackServerProviderImpl.PROVIDER_UUID, "Dummy Action",
				DummyParameterDefinitionA.INSTANCE,
				DummyParameterDefinitionB.INSTANCE);
	}
	
	public static class DummyParameterDefinitionA extends BasicParameterDefinition<StringUnit> {
		
		private static final UUID PARAM_DEF_UUID = UUID.fromString("427fa012-2054-46f3-8bcd-7db55b189524");
		public static final DummyParameterDefinitionA INSTANCE = new DummyParameterDefinitionA();

		public DummyParameterDefinitionA() {
			super(PARAM_DEF_UUID, "Dummy Parameter A", StringUnit.INSTANCE, true);
		}

		@Override
		public String format(String rawValue) {
			return rawValue;
		}

		@Override
		public Collection<String> validate(String value) {
			return Collections.emptyList();
		}
		
	}
	
public static class DummyParameterDefinitionB extends BasicParameterDefinition<StringUnit> {
		
		private static final UUID PARAM_DEF_UUID = UUID.fromString("128fa012-2054-46f3-8bcd-7db55b189524");
		public static final DummyParameterDefinitionB INSTANCE = new DummyParameterDefinitionB();
		
		public DummyParameterDefinitionB() {
			super(PARAM_DEF_UUID, "Dummy Parameter B", StringUnit.INSTANCE, false);
		}

		@Override
		public String format(String rawValue) {
			return rawValue;
		}

		@Override
		public Collection<String> validate(String value) {
			return Collections.emptyList();
		}
		
	}

}
