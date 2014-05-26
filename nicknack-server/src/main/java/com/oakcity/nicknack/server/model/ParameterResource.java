package com.oakcity.nicknack.server.model;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.oakcity.nicknack.core.actions.Action;
import com.oakcity.nicknack.core.actions.Action.Parameter;

@Entity
@Table(name="Parameters")
public class ParameterResource extends ResourceSupport implements Parameter {
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private UUID uuid;
	
	private UUID appliesToParameterDefinition;
	
	private String value;
	
	@ManyToOne(targetEntity=ActionResource.class)
	@JsonIgnore
	private Action action;

	@Override
	public UUID getAppliesToParameterDefinition() {
		return appliesToParameterDefinition;
	}

	@Override
	public String getValue() {
		return value;
	}

	public Action getAction() {
		return action;
	}

	public void setAction(Action action) {
		this.action = action;
	}

	public void setAppliesToParameterDefinition(UUID appliesToParameterDefinition) {
		this.appliesToParameterDefinition = appliesToParameterDefinition;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}
	
	
	

}
