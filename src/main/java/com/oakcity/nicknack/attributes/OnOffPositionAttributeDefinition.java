package com.oakcity.nicknack.attributes;

import com.oakcity.nicknack.Event.Attribute;
import com.oakcity.nicknack.Event.AttributeDefinition;
import com.oakcity.nicknack.units.BooleanUnit;

/** 
 * Provided by device API
 * @author kmbulebu
 *
 */
public class OnOffPositionAttributeDefinition implements AttributeDefinition<BooleanUnit, Boolean> {

	private static final BooleanUnit unit = new BooleanUnit();
	private final String name;
	
	public OnOffPositionAttributeDefinition(String name) {
		this.name = name;
	}

	@Override
	public Attribute<BooleanUnit, Boolean> newInstance(final Boolean value) {
		return new Attribute<BooleanUnit, Boolean>() {
			
			@Override
			public Boolean getValue() {
				return value;
			}
			
			@Override
			public AttributeDefinition<BooleanUnit, Boolean> getAttributeDefinition() {
				return OnOffPositionAttributeDefinition.this;
			}
		};
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getDisplayValue(Boolean value) {
		if (value) {
			return "On";
		} else {
			return "Off";
		}
	}

	@Override
	public BooleanUnit getUnits() {
		return unit;
	}
	
}
