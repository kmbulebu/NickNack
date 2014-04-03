package com.oakcity.nicknack;

import static org.junit.Assert.*;

import org.junit.Test;

import com.oakcity.nicknack.attributes.OnOffPositionAttributeDefinition;
import com.oakcity.nicknack.demo.SwitchEventDefinition;
import com.oakcity.nicknack.filters.EventFilterAndOperator;
import com.oakcity.nicknack.filters.OnOffPositionAttributeFilter;

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
