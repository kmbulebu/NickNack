package com.oakcity.nicknack.events.filters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.oakcity.nicknack.events.AttributeFilter;
import com.oakcity.nicknack.events.AttributeFilterSettings;
import com.oakcity.nicknack.events.Event;
import com.oakcity.nicknack.events.Event.Attribute;
import com.oakcity.nicknack.events.Event.AttributeDefinition;
import com.oakcity.nicknack.events.Event.EventDefinition;
import com.oakcity.nicknack.events.EventFilterOperator;

public class EventFilterAndOperator implements EventFilterOperator {
	
	private EventDefinition appliesTo = null;
	
	private List<AttributeFilterSettings<?>> attributeFilters = new ArrayList<AttributeFilterSettings<?>>();

	@Override
	public List<AttributeFilterSettings<?>> getAttributeFilterSettings() {
		return attributeFilters;
	}

	@Override
	/**
	 * All filters must match. A AND B AND C
	 */
	public boolean matches(Event aThis) {
		if (aThis == null || aThis.getAttributes() == null || attributeFilters.isEmpty()) {
			return false;
		}
		
		if (appliesTo != null && !appliesTo.equals(aThis.getEventDefinition())) {
			return false;
		}
		
		final Map<Class<AttributeDefinition<?, ?>>, List<Attribute<?,?>>> attributeBuckets = bucketizeAttributes(aThis.getAttributes());
		
		boolean match = false;
		for (AttributeFilterSettings<?> attributeFilterSettings : attributeFilters) {
			// Need to create an AttributeFilter instance from the settings.
			AttributeFilter<?, ?, ?> attributeFilter;
			try {
				attributeFilter = attributeFilterSettings.getAttributeFilterClass().newInstance();
			} catch (InstantiationException | IllegalAccessException e) {
				throw new RuntimeException(e);
			}
			
			// Each filter must match all attributes it applies to. 99.9% of the time this will be exactly one.
			final List<Attribute<?,?>> attributes = attributeBuckets.get(attributeFilter.appliesTo());
			
			if (attributes == null) {
				match = false;
			} else {
				match = allAttributesMatch(attributeFilterSettings, attributeFilter, attributes);
			}
			
			// No need to keep checking if all must match.
			if (!match) {
				break;
			}
		}
		
		return match;
	}
	
	@SuppressWarnings("unchecked")
	private boolean allAttributesMatch(@SuppressWarnings("rawtypes") AttributeFilterSettings attributeFilterSettings, final AttributeFilter<?, ?, ?> attributeFilter, final List<Attribute<?,?>> attributes) {
		
		boolean match = false;
		for (@SuppressWarnings("rawtypes") Attribute attribute : attributes) {
			match = attributeFilter.match(attribute, attributeFilterSettings);
			
			if (!match) {
				break;
			}
		}
		return match;
	}
	
	@SuppressWarnings("unchecked")
	private Map<Class<AttributeDefinition<?, ?>>, List<Attribute<?,?>>> bucketizeAttributes(List<Attribute<?,?>> input) {
		Map<Class<AttributeDefinition<?, ?>>, List<Attribute<?,?>>> attributeBuckets = new HashMap<Class<AttributeDefinition<?, ?>>, List<Attribute<?,?>>>();
		
		for (Attribute<?,?> attribute : input) {
			List<Attribute<?,?>> bucket = attributeBuckets.get(attribute.getAttributeDefinition());
			
			if (bucket == null) {
				bucket = new ArrayList<Attribute<?,?>>();
			}
			
			bucket.add(attribute);
			attributeBuckets.put((Class<AttributeDefinition<?, ?>>) attribute.getAttributeDefinition().getClass(), bucket);
		}
		return attributeBuckets;
	}

	@Override
	public EventDefinition appliesTo() {
		return appliesTo;
	}

	public void setAppliesTo(EventDefinition appliesTo) {
		this.appliesTo = appliesTo;
	}

	

}
