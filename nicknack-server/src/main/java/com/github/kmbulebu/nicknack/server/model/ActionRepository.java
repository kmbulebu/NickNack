package com.github.kmbulebu.nicknack.server.model;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.github.kmbulebu.nicknack.core.plans.Plan;

@Repository
public interface ActionRepository extends JpaRepository<ActionResource, UUID> {

	@Query("SELECT a FROM ActionResource a INNER JOIN a.plans p WHERE p IN (:plan)")
	public List<ActionResource> findByPlan(@Param(value = "plan") Plan plan);
	
}
