package com.oakcity.nicknack.units;

import com.oakcity.nicknack.Event.AttributeDefinition.Unit;

public class BooleanUnit implements Unit<Boolean> {

	@Override
	public int compare(Boolean o1, Boolean o2) {
		return o1.compareTo(o2);
	}

	@Override
	public String getName() {
		return "";
	}

	@Override
	public Class<Boolean> getType() {
		return Boolean.class;
	}

	@Override
	public Boolean getMin() {
		return Boolean.FALSE;
	}

	@Override
	public Boolean getMax() {
		return Boolean.TRUE;
	} 

}
