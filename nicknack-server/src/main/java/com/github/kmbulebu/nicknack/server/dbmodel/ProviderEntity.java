package com.github.kmbulebu.nicknack.server.dbmodel;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

@NodeEntity
public class ProviderEntity {

	@GraphId 
	private Long id;
	
	@Indexed(unique=true)
	private UUID uuid;
	
	@RelatedTo(type="HAS_SETTING")
	private Set<AttributeEntity> settings = new HashSet<>();

	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	public Set<AttributeEntity> getSettings() {
		return settings;
	}
	
	public void hasSetting(AttributeEntity setting) {
		settings.add(setting);
	}
	
	
}
