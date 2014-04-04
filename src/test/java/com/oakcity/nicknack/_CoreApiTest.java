package com.oakcity.nicknack;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.oakcity.nicknack.events.AttributeFilterSettings;
import com.oakcity.nicknack.events.Event;
import com.oakcity.nicknack.events.attributes.OnOffPositionAttributeDefinition;
import com.oakcity.nicknack.events.filters.EventFilterAndOperator;
import com.oakcity.nicknack.events.filters.OnOffPositionAttributeFilter;

public class _CoreApiTest {
	
	@Test
	public void testEventFiltering() {
		final EventFilterAndOperator eventFilter = new EventFilterAndOperator();
		
		final SwitchEventDefinition eventDefinition = new SwitchEventDefinition();
		
		// Filtering on SwitchEvents
		eventFilter.setAppliesTo(eventDefinition);
		
		// When ever they switch to On.
		AttributeFilterSettings<Boolean> settings = new OnOffPositionAttributeFilter().newFilterSettings();
		settings.setAttributeName("Position");
		settings.setFilterValues(Boolean.TRUE);

		eventFilter.getAttributeFilterSettings().add(settings);
		
		Event event1 = eventDefinition.newInstance();
		
		event1.getAttributes().add(new OnOffPositionAttributeDefinition("Position").newInstance(Boolean.TRUE));
		
		assertTrue(eventFilter.matches(event1));
		
		Event event2 = eventDefinition.newInstance();
		
		event2.getAttributes().add(new OnOffPositionAttributeDefinition("OtherThing").newInstance(Boolean.TRUE));
		
		assertFalse(eventFilter.matches(event2));
		
		Event event3 = eventDefinition.newInstance();
		
		event3.getAttributes().add(new OnOffPositionAttributeDefinition("Position").newInstance(Boolean.FALSE));
		
		assertFalse(eventFilter.matches(event3));
	}

}
