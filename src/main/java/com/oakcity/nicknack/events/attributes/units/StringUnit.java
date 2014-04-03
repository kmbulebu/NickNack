package com.oakcity.nicknack.events.attributes.units;

import com.oakcity.nicknack.events.Event.AttributeDefinition.Unit;

public class StringUnit implements Unit<String> {

	@Override
	public int compare(String o1, String o2) {
		return o1.compareTo(o2);
	}

	@Override
	public String getName() {
		return "";
	}

	@Override
	public Class<String> getType() {
		return String.class;
	}

	@Override
	public String getMin() {
		return null; // unbounded
	}

	@Override
	public String getMax() {
		return null; // unbounded
	} 

}
