package com.github.kmbulebu.nicknack.core.attributes.filters;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.junit.Test;

import com.github.kmbulebu.nicknack.core.attributes.AttributeDefinition;
import com.github.kmbulebu.nicknack.core.attributes.BasicAttributeDefinition;
import com.github.kmbulebu.nicknack.core.events.BasicEventDefinition;
import com.github.kmbulebu.nicknack.core.units.BooleanUnit;
import com.github.kmbulebu.nicknack.core.units.StringUnit;

public class AttributeFitlerExpressionCollectionEvaluatorTest {
	
	@Test
	public void testAttributeFilterEvaluate() throws ParseException {
		AttrbuteFilterExpressionCollectionEvaluator evaluator = new AttrbuteFilterExpressionCollectionEvaluator();
		
		final Map<UUID, String> attributeValues = new HashMap<UUID, String>();
		
		// No attribute values, should be false.
		assertFalse(evaluator.evaluate(POSITION_ATTRIBUTE_DEF.getUUID(), positionFalseAttributeFilter, attributeValues, Collections.singletonList(POSITION_ATTRIBUTE_DEF)));
		
		// Non matching value, should be false;
		attributeValues.put(POSITION_ATTRIBUTE_DEF.getUUID(), "true");
		assertFalse(evaluator.evaluate(POSITION_ATTRIBUTE_DEF.getUUID(), positionFalseAttributeFilter, attributeValues, Collections.singletonList(POSITION_ATTRIBUTE_DEF)));
		
		// Matching value, should be true;
		attributeValues.put(POSITION_ATTRIBUTE_DEF.getUUID(), "false");
		assertTrue(evaluator.evaluate(POSITION_ATTRIBUTE_DEF.getUUID(), positionFalseAttributeFilter, attributeValues, Collections.singletonList(POSITION_ATTRIBUTE_DEF)));
				
		// Matching value, but no definition.
		assertFalse(evaluator.evaluate(POSITION_ATTRIBUTE_DEF.getUUID(), positionFalseAttributeFilter, attributeValues, new ArrayList<AttributeDefinition>()));
				
		// Matching value, should be true;
		// Adding some addition values.
		attributeValues.put(MAC_ADDRESS_ATTRIBUTE_DEF.getUUID(), "0b:41:c1:3a:89:36");
		assertTrue(evaluator.evaluate(POSITION_ATTRIBUTE_DEF.getUUID(), positionFalseAttributeFilter, attributeValues, Collections.singletonList(POSITION_ATTRIBUTE_DEF)));
				
		// Matching value, should be true;
		// Adding some addition values and definitions.
		final List<AttributeDefinition> attributeDefinitions = new ArrayList<AttributeDefinition>();
		attributeDefinitions.add(POSITION_ATTRIBUTE_DEF);
		attributeDefinitions.add(MAC_ADDRESS_ATTRIBUTE_DEF);
		assertTrue(evaluator.evaluate(POSITION_ATTRIBUTE_DEF.getUUID(), positionFalseAttributeFilter, attributeValues, Collections.singletonList(POSITION_ATTRIBUTE_DEF)));	
	}
	
	@Test
	public void testGetApplicableAttributeDefinition() {
		AttrbuteFilterExpressionCollectionEvaluator evaluator = new AttrbuteFilterExpressionCollectionEvaluator();
		
		final List<AttributeDefinition> attributeDefinitions = new ArrayList<AttributeDefinition>();
		attributeDefinitions.add(POSITION_ATTRIBUTE_DEF);
		attributeDefinitions.add(MAC_ADDRESS_ATTRIBUTE_DEF);
		
		assertEquals(POSITION_ATTRIBUTE_DEF, evaluator.getApplicableAttributeDefinition(POSITION_ATTRIBUTE_DEF.getUUID(), attributeDefinitions));
		assertNotEquals(MAC_ADDRESS_ATTRIBUTE_DEF, evaluator.getApplicableAttributeDefinition(POSITION_ATTRIBUTE_DEF.getUUID(), attributeDefinitions));
		
		
		assertEquals(MAC_ADDRESS_ATTRIBUTE_DEF, evaluator.getApplicableAttributeDefinition(MAC_ADDRESS_ATTRIBUTE_DEF.getUUID(), attributeDefinitions));
		assertNotEquals(POSITION_ATTRIBUTE_DEF, evaluator.getApplicableAttributeDefinition(MAC_ADDRESS_ATTRIBUTE_DEF.getUUID(), attributeDefinitions));
		
	}
	
	public static AttributeDefinition POSITION_ATTRIBUTE_DEF = new BasicAttributeDefinition(UUID.fromString("320c68e0-d662-11e3-9c1a-0800200d9a66"), "position", new BooleanUnit(), false);
	public static AttributeDefinition MAC_ADDRESS_ATTRIBUTE_DEF = new BasicAttributeDefinition(UUID.fromString("920c68e0-d662-31e3-9c1a-0800200d9a66"), "macAddress", new StringUnit(), false);

	
	public final static AttributeFilterExpression positionFalseAttributeFilter = new AttributeFilterExpression() {


		@Override
		public Operator getOperator() {
			return Operator.EQUALS;
		}

		@Override
		public String getOperand() {
			return "false";
		}

		@Override
		public UUID getAttributeDefinitionUuid() {
			return POSITION_ATTRIBUTE_DEF.getUUID();
		}
		
	};
	

	public final static AttributeFilterExpression macAddresseAttributeFilter = new AttributeFilterExpression() {

		@Override
		public Operator getOperator() {
			return Operator.EQUALS;
		}

		@Override
		public String getOperand() {
			return "0b:41:c1:3a:89:36";
		}

		@Override
		public UUID getAttributeDefinitionUuid() {
			return MAC_ADDRESS_ATTRIBUTE_DEF.getUUID();
		}
		
	};

	public static class SwitchChangeEventDefinition extends BasicEventDefinition {
		
		public SwitchChangeEventDefinition() {
			super(UUID.fromString("320c68e0-d662-11e3-9c1a-0800200c9a66"), 
					"Switch Changed", 
					POSITION_ATTRIBUTE_DEF,
					MAC_ADDRESS_ATTRIBUTE_DEF);
		}
		
	}

	static final SwitchChangeEventDefinition SWITCH_CHANGE_EVENT_DEF = new SwitchChangeEventDefinition();
	
	
	
}
