package com.oakcity.nicknack.events;

public interface AttributeFilterSettings<ValueType> {
	
	public String getAttributeName();
	
	public Class<AttributeFilter<?, ?, ValueType>> getAttributeFilterClass();
	
	public ValueType[] getFilterValues();
	
	@SuppressWarnings("unchecked")
	public void setFilterValues(ValueType... values);
	
	public void setAttributeName(String attributeName);
	

}
