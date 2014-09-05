package com.oakcity.nicknack.core;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.junit.Test;

import com.oakcity.nicknack.core.SwitchChangeEvent.SwitchPositionAttribute;
import com.oakcity.nicknack.core.actions.Action;
import com.oakcity.nicknack.core.events.Event;
import com.oakcity.nicknack.core.events.EventDefinition;
import com.oakcity.nicknack.core.events.filters.AttributeFilter;
import com.oakcity.nicknack.core.events.filters.EventFilter;
import com.oakcity.nicknack.core.events.filters.EventFilterEvaluator;
import com.oakcity.nicknack.core.events.filters.operators.Operator;
import com.oakcity.nicknack.core.plans.Plan;

public class _CoreApiTest {
	
	@Test
	public void testEventFiltering() throws ParseException {
		
		// Step 2: User creates an Event Filter
		final EventFilter switchEventFilter = new EventFilter() {


			@Override
			public String getDescription() {
				return "Switch is turned on.";
			}

			@Override
			public List<AttributeFilter> getAttributeFilters() {
				// Step 3: User creates an Attribute filter for their Event Filter.
				AttributeFilter filter = new AttributeFilter() {

					@Override
					public Operator getOperator() {
						return Operator.EQUALS;
					}

					@Override
					public String getOperand() {
						return "true";
					}
					
					@Override
					public UUID getAppliesToAttributeDefinition() {
						// TODO Clean up all these unnecessary attribute instantiations. A by-product of refactoring.
						return new SwitchPositionAttribute().getUUID();
					}
					
				};
				return Collections.singletonList(filter);
			}

			@Override
			public UUID getAppliesToEventDefinition() {
				return new SwitchChangeEvent().getUUID();
			}
			
		};
		
		
		
		// Step 1: User creates a new Plan
		Plan newPlan = new Plan() {

			@Override
			public UUID getUUID() {
				return UUID.fromString("44a84940-d664-11e3-9c1a-0800200c9a66");
			}

			@Override
			public String getName() {
				return "My Plan";
			}

			@Override
			public List<EventFilter> getEventFilters() {
				return Collections.singletonList(switchEventFilter);
			}

			@Override
			public List<Action> getActions() {
				return null; // Not yet implemented.
			}
			
		};
		
		// Whole thing is mapped out. Let's create an event and see if it matches the filter.
		
		// Create an event.
		Event switchOnEvent = new Event() {

			@Override
			public Map<UUID, String> getAttributes() {
				Map<UUID, String> attributes = new HashMap<UUID, String>();
				attributes.put(new SwitchPositionAttribute().getUUID(), "true");
				return attributes;
			}

			@Override
			public EventDefinition getEventDefinition() {
				return new SwitchChangeEvent();
			}
			
			@Override
			public Date getCreated() {
				// TODO Auto-generated method stub
				return null;
			}
			
		};
		
		Event switchOffEvent = new Event() {

			@Override
			public Map<UUID, String> getAttributes() {
				Map<UUID, String> attributes = new HashMap<UUID, String>();
				attributes.put(new SwitchPositionAttribute().getUUID(), "false");
				return attributes;
			}

			@Override
			public EventDefinition getEventDefinition() {
				return new SwitchChangeEvent();
			}

			@Override
			public Date getCreated() {
				// TODO Auto-generated method stub
				return null;
			}
			
		};
		
		// See if the event matches the filter.
		EventFilterEvaluator evaluator = new EventFilterEvaluator();
		
		assertTrue(evaluator.evaluate(switchEventFilter, switchOnEvent));
		
		assertFalse(evaluator.evaluate(switchEventFilter, switchOffEvent));
		
	}

}
