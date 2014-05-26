package com.oakcity.nicknack.server.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.oakcity.nicknack.core.actions.Action;
import com.oakcity.nicknack.core.plans.Plan;

@Entity
@Table(name="Actions")
public class ActionResource extends ResourceSupport implements Action {
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private UUID uuid;
	
	private UUID appliesToActionDefinition;
	
	@OneToMany(targetEntity=ParameterResource.class)
	@JsonIgnore
	private List<Parameter> parameters = new ArrayList<Parameter>();
	
	@ManyToOne(targetEntity=PlanResource.class)
	@JsonIgnore
	private Plan plan;
	
	@Override
	public UUID getAppliesToActionDefinition() {
		return appliesToActionDefinition;
	}

	@Override
	public List<Parameter> getParameters() {
		return parameters;
	}

	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	public void setAppliesToActionDefinition(UUID appliesToActionDefinition) {
		this.appliesToActionDefinition = appliesToActionDefinition;
	}

	public Plan getPlan() {
		return plan;
	}

	public void setPlan(Plan plan) {
		this.plan = plan;
	}

	public void setParameters(List<Parameter> parameters) {
		this.parameters = parameters;
	}
	
}
