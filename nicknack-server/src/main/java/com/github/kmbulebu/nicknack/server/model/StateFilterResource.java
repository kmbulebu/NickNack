package com.github.kmbulebu.nicknack.server.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.github.kmbulebu.nicknack.core.attributes.filters.AttributeFilterExpression;
import com.github.kmbulebu.nicknack.core.states.filters.StateFilter;
import com.github.kmbulebu.nicknack.core.plans.Plan;

@Entity
@Table(name="StateFilters")
@Relation(value="StateFilter", collectionRelation="StateFilters")
public class StateFilterResource extends ResourceSupport implements StateFilter {
	
	@Id
	@Column(updatable=false)
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private UUID uuid;
	
	@NotNull
	private UUID appliesToStateDefinition;
	
	private String description;
	
	@JsonSerialize(contentAs=AttributeFilterExpressionImpl.class)
	@JsonDeserialize(contentAs=AttributeFilterExpressionImpl.class)
	@ElementCollection(targetClass=AttributeFilterExpressionImpl.class, fetch=FetchType.EAGER)
	private Collection<AttributeFilterExpression> attributeFilterExpressions = new ArrayList<>();
	
	@ManyToOne(targetEntity=PlanResource.class)
	@JsonIgnore
	private Plan plan;
	
	@Override
	public UUID getAppliesToStateDefinition() {
		return appliesToStateDefinition;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public Collection<AttributeFilterExpression>  getAttributeFilterExpressions() {
		return attributeFilterExpressions;
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

	public void setAppliesToStateDefinition(UUID appliesToStateDefinition) {
		this.appliesToStateDefinition = appliesToStateDefinition;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public void setAttributeFilterExpressions(Collection<AttributeFilterExpression> attributeFilterExpressions) {
		this.attributeFilterExpressions = attributeFilterExpressions;
	}

}
