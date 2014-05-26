package com.oakcity.nicknack.server.services;

import java.util.List;
import java.util.UUID;

import com.oakcity.nicknack.server.model.AttributeFilterResource;

public interface AttributeFiltersService {
	
	public List<AttributeFilterResource> getAttributeFilters(UUID eventUuid);
	
	public AttributeFilterResource getAttributeFilter(UUID uuid);
	
	public void deleteAttributeFilter(UUID uuid);
	
	public AttributeFilterResource createAttributeFilter(UUID eventUuid, AttributeFilterResource newAttributeFilter);
	
	public AttributeFilterResource modifyAttributeFilter(AttributeFilterResource modifiedAttributeFilter);

}
