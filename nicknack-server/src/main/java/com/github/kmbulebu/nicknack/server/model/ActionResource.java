package com.github.kmbulebu.nicknack.server.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.kmbulebu.nicknack.core.actions.Action;
import com.github.kmbulebu.nicknack.core.plans.Plan;

@Entity
@Table(name="Actions")
@Relation(value="Action", collectionRelation="Actions")
public class ActionResource extends ResourceSupport implements Action {
	
	@Id
	@Column(updatable=false)
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private UUID uuid;
	
	@NotNull
	private UUID appliesToActionDefinition;
	
	@NotNull
	private UUID appliesToProvider;
	
	@NotNull
	@ElementCollection(fetch = FetchType.EAGER)
	private Map<UUID, String> parameters = new HashMap<UUID, String>();
	
	@ManyToMany(targetEntity=PlanResource.class)
	@JsonIgnore
	private Set<Plan> plans = new HashSet<Plan>();
	
	private String description;
	
	@Override
	public UUID getAppliesToActionDefinition() {
		return appliesToActionDefinition;
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

	public Set<Plan> getPlans() {
		return plans;
	}

	public void setPlans(Set<Plan> plans) {
		this.plans = plans;
	}

	@Override
	public Map<UUID, String> getAttributes() {
		return parameters;
	}

	public void setParameters(Map<UUID, String> parameters) {
		this.parameters = parameters;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public UUID getAppliesToProvider() {
		return appliesToProvider;
	}

	public void setAppliesToProvider(UUID appliesToProvider) {
		this.appliesToProvider = appliesToProvider;
	}

	@Override
	public String toString() {
		return "ActionResource [uuid=" + uuid + ", appliesToActionDefinition=" + appliesToActionDefinition
				+ ", parameters=" + parameters + "]";
	}
	
	
	
}
