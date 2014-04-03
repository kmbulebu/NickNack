package com.oakcity.nicknack.events.filters;

import com.oakcity.nicknack.events.AttributeFilter;
import com.oakcity.nicknack.events.AttributeFilterSettings;
import com.oakcity.nicknack.events.Event.Attribute;
import com.oakcity.nicknack.events.attributes.OnOffPositionAttributeDefinition;
import com.oakcity.nicknack.events.attributes.units.BooleanUnit;

public class OnOffPositionAttributeFilter implements AttributeFilter<OnOffPositionAttributeDefinition, BooleanUnit, Boolean> {

	@Override
	public boolean match(Attribute<BooleanUnit, Boolean> attribute, AttributeFilterSettings<Boolean> settings) {
		if (settings.getFilterValues() == null) {
			return false;
		}
		
		if (!attribute.getAttributeDefinition().getName().equals(settings.getAttributeName())) {
			return false;
		}
		
		for (Boolean matchValue : settings.getFilterValues()) {
			if (matchValue.equals(attribute.getValue())) {
				return true;
			}
		}
		return false;
	}

	
	@Override
	public String getDescription() {
		return "Matches if the switch is in the specified position.";
	}

	@Override
	public Class<OnOffPositionAttributeDefinition> appliesTo() {
		return OnOffPositionAttributeDefinition.class;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public AttributeFilterSettings<Boolean> newFilterSettings() {
		return new AttributeFilterSettingsImpl<OnOffPositionAttributeDefinition, BooleanUnit, Boolean>((Class<AttributeFilter<?, ?, Boolean>>) this.getClass());
	}

}
