package com.oakcity.nicknack.filters;

import com.oakcity.nicknack.AttributeFilter;
import com.oakcity.nicknack.AttributeFilterSettings;
import com.oakcity.nicknack.Event.AttributeDefinition;
import com.oakcity.nicknack.Event.AttributeDefinition.Unit;

public class AttributeFilterSettingsImpl<U extends AttributeDefinition<V , ValueType>, V extends Unit<ValueType>,  ValueType> implements AttributeFilterSettings<ValueType> {
	
	private ValueType[] filterValues;
	private Class<AttributeFilter<?, ?, ValueType>> filterClass;
	private String attributeName;
	
	protected AttributeFilterSettingsImpl(Class<AttributeFilter<?, ?, ValueType>> filterClass) {
		this.filterClass = filterClass;
	}

	@Override
	public String getAttributeName() {
		return attributeName;
	}

	@Override
	public Class<AttributeFilter<?, ?, ValueType>> getAttributeFilterClass() {
		return filterClass;
	}

	@Override
	public ValueType[] getFilterValues() {
		return filterValues;
	}
	
	@Override
	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}

	@Override
	public void setFilterValues(@SuppressWarnings("unchecked") ValueType... values) {
		this.filterValues = values;
	}

}
