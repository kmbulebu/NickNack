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

import com.oakcity.nicknack.core.actions.Action;

@Entity
@Table(name="Actions")
public class ActionResource extends ResourceSupport implements Action {
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private UUID uuid;
	
	private UUID appliestoActionDefinition;
	
	@OneToMany(targetEntity=ParameterResource.class)
	private List<Parameter> parameters = new ArrayList<Parameter>();

	@Override
	public UUID getAppliesToActionDefinition() {
		return appliestoActionDefinition;
	}

	@Override
	public List<Parameter> getParameters() {
		// TODO Auto-generated method stub
		return null;
	}

}
