package com.github.kmbulebu.nicknack.core.events.filters;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.junit.Test;

import com.github.kmbulebu.nicknack.core.events.AttributeDefinition;
import com.github.kmbulebu.nicknack.core.events.BasicAttributeDefinition;
import com.github.kmbulebu.nicknack.core.events.BasicEventDefinition;
import com.github.kmbulebu.nicknack.core.events.Event;
import com.github.kmbulebu.nicknack.core.events.EventDefinition;
import com.github.kmbulebu.nicknack.core.events.filters.AttributeFilterExpression;
import com.github.kmbulebu.nicknack.core.events.filters.EventFilter;
import com.github.kmbulebu.nicknack.core.events.filters.EventFilterEvaluator;
import com.github.kmbulebu.nicknack.core.events.filters.operators.Operator;
import com.github.kmbulebu.nicknack.core.units.BooleanUnit;
import com.github.kmbulebu.nicknack.core.units.StringUnit;

public class _EventFilterEvaluatorTest {
	
	@Test
	public void testAttributeFilterEvaluate() throws ParseException {
		EventFilterEvaluator evaluator = new EventFilterEvaluator();
		
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
		EventFilterEvaluator evaluator = new EventFilterEvaluator();
		
		final List<AttributeDefinition> attributeDefinitions = new ArrayList<AttributeDefinition>();
		attributeDefinitions.add(POSITION_ATTRIBUTE_DEF);
		attributeDefinitions.add(MAC_ADDRESS_ATTRIBUTE_DEF);
		
		assertEquals(POSITION_ATTRIBUTE_DEF, evaluator.getApplicableAttributeDefinition(POSITION_ATTRIBUTE_DEF.getUUID(), attributeDefinitions));
		assertNotEquals(MAC_ADDRESS_ATTRIBUTE_DEF, evaluator.getApplicableAttributeDefinition(POSITION_ATTRIBUTE_DEF.getUUID(), attributeDefinitions));
		
		
		assertEquals(MAC_ADDRESS_ATTRIBUTE_DEF, evaluator.getApplicableAttributeDefinition(MAC_ADDRESS_ATTRIBUTE_DEF.getUUID(), attributeDefinitions));
		assertNotEquals(POSITION_ATTRIBUTE_DEF, evaluator.getApplicableAttributeDefinition(MAC_ADDRESS_ATTRIBUTE_DEF.getUUID(), attributeDefinitions));
		
	}
	
	@Test
	public void testEvaluate() throws ParseException {
		EventFilterEvaluator evaluator = new EventFilterEvaluator();
		
		final Event event = new Event() {

			@Override
			public Map<UUID, String> getAttributes() {
				final Map<UUID, String> attributes = new HashMap<UUID, String>();
				attributes.put(POSITION_ATTRIBUTE_DEF.getUUID(), "true");
				attributes.put(MAC_ADDRESS_ATTRIBUTE_DEF.getUUID(), "0b:41:c1:3a:89:36");
				return attributes;
			}

			@Override
			public EventDefinition getEventDefinition() {
				return SWITCH_CHANGE_EVENT_DEF;
			}

			@Override
			public Date getCreated() {
				// TODO Auto-generated method stub
				return null;
			}
			
		};
		
		// Filter asks for false position, event says true. 
		assertFalse(evaluator.evaluate(eventFilter, event));
		
		final Event event2 = new Event() {

			@Override
			public Map<UUID, String> getAttributes() {
				final Map<UUID, String> attributes = new HashMap<UUID, String>();
				attributes.put(POSITION_ATTRIBUTE_DEF.getUUID(), "false");
				attributes.put(MAC_ADDRESS_ATTRIBUTE_DEF.getUUID(), "0b:41:c1:3a:89:36");
				return attributes;
			}

			@Override
			public EventDefinition getEventDefinition() {
				return SWITCH_CHANGE_EVENT_DEF;
			}

			@Override
			public Date getCreated() {
				// TODO Auto-generated method stub
				return null;
			}
			
		};
		
		// Filter asks for false position, event says false. 
		assertTrue(evaluator.evaluate(eventFilter, event2));
		
		final Event event3 = new Event() {

			@Override
			public Map<UUID, String> getAttributes() {
				final Map<UUID, String> attributes = new HashMap<UUID, String>();
				attributes.put(POSITION_ATTRIBUTE_DEF.getUUID(), "false");
				attributes.put(MAC_ADDRESS_ATTRIBUTE_DEF.getUUID(), "0b:41:c1:3a:90:36");
				return attributes;
			}

			@Override
			public EventDefinition getEventDefinition() {
				return SWITCH_CHANGE_EVENT_DEF;
			}

			@Override
			public Date getCreated() {
				// TODO Auto-generated method stub
				return null;
			}
			
		};
		
		// Wrong mac
		assertFalse(evaluator.evaluate(eventFilter, event3));
				
		
	}
	
	
	
	
	
	
	public static AttributeDefinition POSITION_ATTRIBUTE_DEF = new BasicAttributeDefinition(UUID.fromString("320c68e0-d662-11e3-9c1a-0800200d9a66"), "position", new BooleanUnit(), false);
	public static AttributeDefinition MAC_ADDRESS_ATTRIBUTE_DEF = new BasicAttributeDefinition(UUID.fromString("920c68e0-d662-31e3-9c1a-0800200d9a66"), "macAddress", new StringUnit(), false);
	
	public final static EventFilter eventFilter = new EventFilter() {

		@Override
		public UUID getAppliesToEventDefinition() {
			return UUID.fromString("320c68e0-d662-11e3-9c1a-0800200c9a66");

		}

		@Override
		public String getDescription() {
			return "Test Event Filter";
		}

		@Override
		public Collection<AttributeFilterExpression> getAttributeFilterExpressions() {
			final Collection<AttributeFilterExpression>attributeFilters = new ArrayList<>();
			attributeFilters.add(positionFalseAttributeFilter);
			attributeFilters.add(macAddresseAttributeFilter);
			return attributeFilters;
		}
		
	};
	
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