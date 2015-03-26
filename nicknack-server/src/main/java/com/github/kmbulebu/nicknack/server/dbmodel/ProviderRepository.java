package com.github.kmbulebu.nicknack.server.dbmodel;

import java.util.UUID;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;

public interface ProviderRepository extends GraphRepository<ProviderEntity> {

	ProviderEntity findByUuid(UUID uuid);

	@Query("MATCH (p)-[:HAS_SETTING]->(a) WHERE p:ProviderEntity and a:AttributeEntity and p.uuid = {0} and a.attributeDefinitionUuid={1} RETURN a")
	AttributeEntity findSetting(UUID uuid, UUID attributeDefinitionUuid);
	
}
