package com.github.kmbulebu.nicknack.server.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.kmbulebu.nicknack.core.actions.Action;
import com.github.kmbulebu.nicknack.core.events.filters.EventFilter;
import com.github.kmbulebu.nicknack.core.plans.Plan;
import com.github.kmbulebu.nicknack.core.states.filters.StateFilter;

@Entity
@Table(name="Plans")
@Relation(value="Plan", collectionRelation="Plans")
public class PlanResource extends ResourceSupport implements Plan {
	
	@Id
	@Column(updatable=false)
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private UUID uuid;
	
	private String name;
	
	@NotNull
	@OneToMany(targetEntity=EventFilterResource.class, mappedBy="plan", cascade=CascadeType.ALL)
	@JsonIgnore
	private List<EventFilter> eventFilters = new ArrayList<EventFilter>();
	
	@NotNull
	@OneToMany(targetEntity=StateFilterResource.class, mappedBy="plan", cascade=CascadeType.ALL)
	@JsonIgnore
	private List<StateFilter> stateFilters = new ArrayList<StateFilter>();
	
	@NotNull
	@ManyToMany(targetEntity=ActionResource.class, cascade=CascadeType.ALL)
	@JsonIgnore
	private List<Action> actions = new ArrayList<Action>();

	@Override
	public UUID getUUID() {
		return uuid;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public List<EventFilter> getEventFilters() {
		return eventFilters;
	}
	
	@Override
	public List<StateFilter> getStateFilters() {
		return stateFilters;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setEventFilters(List<EventFilter> eventFilters) {
		this.eventFilters = eventFilters;
	}
	
	public void setStateFilters(List<StateFilter> stateFilters) {
		this.stateFilters = stateFilters;
	}

	public List<Action> getActions() {
		return actions;
	}

	public void setActions(List<Action> actions) {
		this.actions = actions;
	}




	
	

}
