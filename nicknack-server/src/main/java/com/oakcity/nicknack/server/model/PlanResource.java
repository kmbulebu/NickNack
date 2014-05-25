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
import org.hibernate.annotations.Type;
import org.springframework.hateoas.ResourceSupport;

import com.oakcity.nicknack.core.actions.Action;
import com.oakcity.nicknack.core.events.filters.EventFilter;
import com.oakcity.nicknack.core.plans.Plan;

@Entity
@Table(name="Plans")
public class PlanResource extends ResourceSupport implements Plan {
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private UUID uuid;
	
	private String name;
	
	@OneToMany(targetEntity=EventFilterResource.class)
	private List<EventFilter> eventFilters = new ArrayList<EventFilter>();
	
	//private ActionResource action;

	@Override
	public UUID getUUID() {
		return uuid;
	}

	@Override
	public String name() {
		return name;
	}

	@Override
	public List<EventFilter> getEventFilters() {
		return eventFilters;
	}

	@Override
	public Action getAction() {
		//return action;
		return null;
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

	/*
	public void setAction(Action action) {
		this.action = action;
	}*/
	
	

}
