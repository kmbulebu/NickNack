package com.github.kmbulebu.nicknack.server.model;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.github.kmbulebu.nicknack.core.plans.Plan;

@Repository
public interface StateFilterRepository extends JpaRepository<StateFilterResource, UUID> {

	public List<StateFilterResource> findByPlan(Plan plan);
	
}
