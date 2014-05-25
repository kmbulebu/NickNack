package com.oakcity.nicknack.server.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.hateoas.ResourceSupport;

import com.oakcity.nicknack.core.events.filters.EventFilter;

@Entity
@Table(name="EventFilters")
public class EventFilterResource extends ResourceSupport implements EventFilter {
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private UUID uuid;
	
	private UUID appliesToEventDefinition;
	
	private String description;
	
	@OneToMany(targetEntity=AttributeFilterResource.class)
	private List<AttributeFilter> attributeFilters = new ArrayList<AttributeFilter>();
	
	@Override
	public UUID getAppliesToEventDefinition() {
		return appliesToEventDefinition;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public List<AttributeFilter> getAttributeFilters() {
		return attributeFilters;
	}

}
