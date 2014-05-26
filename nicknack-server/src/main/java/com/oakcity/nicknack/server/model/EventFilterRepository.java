package com.oakcity.nicknack.server.model;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.oakcity.nicknack.core.plans.Plan;

@Repository
public interface EventFilterRepository extends JpaRepository<EventFilterResource, UUID> {

	public List<EventFilterResource> findByPlan(Plan plan);
	
}
