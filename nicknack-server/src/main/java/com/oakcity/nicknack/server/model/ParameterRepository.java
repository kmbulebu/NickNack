package com.oakcity.nicknack.server.model;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.oakcity.nicknack.core.actions.Action;

@Repository
public interface ParameterRepository extends JpaRepository<ParameterResource, UUID> {

	public List<ParameterResource> findByAction(Action action);
	
}
