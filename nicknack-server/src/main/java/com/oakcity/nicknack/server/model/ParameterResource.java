package com.oakcity.nicknack.server.model;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.hateoas.ResourceSupport;

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

	@Override
	public UUID getAppliesToParameterDefinition() {
		return appliesToParameterDefinition;
	}

	@Override
	public String getValue() {
		return value;
	}

}
