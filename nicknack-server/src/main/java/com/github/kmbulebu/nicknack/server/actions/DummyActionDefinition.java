package com.github.kmbulebu.nicknack.server.actions;

import java.util.UUID;

import com.github.kmbulebu.nicknack.core.actions.BasicActionDefinition;
import com.github.kmbulebu.nicknack.core.attributes.BasicAttributeDefinition;
import com.github.kmbulebu.nicknack.core.valuetypes.TextType;
import com.github.kmbulebu.nicknack.core.valuetypes.WholeNumberType;

public class DummyActionDefinition extends BasicActionDefinition {
	
	private static final UUID ACTION_DEF_UUID = UUID.fromString("327fa012-2054-46f3-8bcd-7db55b179524");
	
	public static final DummyActionDefinition INSTANCE = new DummyActionDefinition();

	public DummyActionDefinition() {
		super(ACTION_DEF_UUID, "Dummy Action",
				"Reserved for testing and development",
				DummyAttributeDefinitionA.INSTANCE,
				DummyAttributeDefinitionB.INSTANCE);
	}
	
	public static class DummyAttributeDefinitionA extends BasicAttributeDefinition<TextType> {
		
		private static final UUID PARAM_DEF_UUID = UUID.fromString("427fa012-2054-46f3-8bcd-7db55b189524");
		public static final DummyAttributeDefinitionA INSTANCE = new DummyAttributeDefinitionA();

		public DummyAttributeDefinitionA() {
			super(PARAM_DEF_UUID, "Dummy Text Parameter", new TextType(), true);
		}

	}
	
public static class DummyAttributeDefinitionB extends BasicAttributeDefinition<WholeNumberType> {
		
		private static final UUID PARAM_DEF_UUID = UUID.fromString("128fa012-2054-46f3-8bcd-7db55b189524");
		public static final DummyAttributeDefinitionB INSTANCE = new DummyAttributeDefinitionB();
		
		public DummyAttributeDefinitionB() {
			super(PARAM_DEF_UUID, "Dummy Number Parameter", new WholeNumberType(0, 100, 10), false);
		}
		
	}

}
