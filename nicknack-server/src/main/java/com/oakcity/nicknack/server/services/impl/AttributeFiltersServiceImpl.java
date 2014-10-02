package com.oakcity.nicknack.server.services.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oakcity.nicknack.core.events.filters.EventFilter;
import com.oakcity.nicknack.server.model.AttributeFilterRepository;
import com.oakcity.nicknack.server.model.AttributeFilterResource;
import com.oakcity.nicknack.server.model.EventFilterRepository;
import com.oakcity.nicknack.server.model.EventFilterResource;
import com.oakcity.nicknack.server.services.AttributeFiltersService;

@Service
public class AttributeFiltersServiceImpl implements AttributeFiltersService {
	
	@Autowired
	private EventFilterRepository eventRepo;
	
	@Autowired
	private AttributeFilterRepository attributeFilterRepo;;

	@Override
	public List<AttributeFilterResource> getAttributeFilters(UUID eventUuid) {
		final EventFilter eventFilter = eventRepo.findOne(eventUuid);
		return attributeFilterRepo.findByEventFilter(eventFilter);
	}

	@Override
	public AttributeFilterResource getAttributeFilter(UUID uuid) {
		return attributeFilterRepo.findOne(uuid);
	}

	@Override
	@Transactional
	public void deleteAttributeFilter(UUID uuid) {
		final AttributeFilterResource existing = attributeFilterRepo.findOne(uuid);
		existing.getEventFilter().getAttributeFilters().remove(existing);
		attributeFilterRepo.delete(uuid);
	}

	@Override
	@Transactional
	public AttributeFilterResource createAttributeFilter(UUID eventUuid, AttributeFilterResource newAttributeFilter) {
		final EventFilter eventFilter = eventRepo.findOne(eventUuid);
		newAttributeFilter.setEventFilter(eventFilter);
		
		
		final AttributeFilterResource resource = attributeFilterRepo.save(newAttributeFilter);
		eventFilter.getAttributeFilters().add(newAttributeFilter);
		eventRepo.save((EventFilterResource) eventFilter);
		return resource;
	}

	@Override
	@Transactional
	public AttributeFilterResource modifyAttributeFilter(AttributeFilterResource modifiedAttributeFilter) {
		final AttributeFilterResource existing = attributeFilterRepo.findOne(modifiedAttributeFilter.getUuid());
		modifiedAttributeFilter.setEventFilter(existing.getEventFilter());
		final AttributeFilterResource resource = attributeFilterRepo.save(modifiedAttributeFilter);
		return resource;
	}

}
