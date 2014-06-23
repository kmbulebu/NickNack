package com.oakcity.nicknack.server.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.oakcity.nicknack.core.events.filters.EventFilter;
import com.oakcity.nicknack.core.plans.Plan;

@Entity
@Table(name="EventFilters")
@Relation(value="EventFilter", collectionRelation="EventFilters")
public class EventFilterResource extends ResourceSupport implements EventFilter {
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private UUID uuid;
	
	@NotNull
	private UUID appliesToEventDefinition;
	
	private String description;
	
	@NotNull
	@OneToMany(targetEntity=AttributeFilterResource.class, fetch=FetchType.EAGER)
	private List<AttributeFilter> attributeFilters = new ArrayList<AttributeFilter>();
	
	@ManyToOne(targetEntity=PlanResource.class)
	@JsonIgnore
	private Plan plan;
	
	@Override
	public UUID getAppliesToEventDefinition() {
		return appliesToEventDefinition;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	@JsonIgnore
	public List<AttributeFilter> getAttributeFilters() {
		return attributeFilters;
	}

	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	public Plan getPlan() {
		return plan;
	}

	public void setPlan(Plan plan) {
		this.plan = plan;
	}

	public void setAppliesToEventDefinition(UUID appliesToEventDefinition) {
		this.appliesToEventDefinition = appliesToEventDefinition;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setAttributeFilters(List<AttributeFilter> attributeFilters) {
		this.attributeFilters = attributeFilters;
	}

}
