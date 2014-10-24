package com.github.kmbulebu.nicknack.server.model;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.github.kmbulebu.nicknack.core.events.filters.EventFilter;

@Repository
public interface AttributeFilterRepository extends JpaRepository<AttributeFilterResource, UUID> {

	public List<AttributeFilterResource> findByEventFilter(EventFilter eventFilter);
	
}
