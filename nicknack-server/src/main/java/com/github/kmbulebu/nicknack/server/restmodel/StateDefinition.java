package com.github.kmbulebu.nicknack.server.restmodel;

import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonView;

public interface StateDefinition {

	@JsonView(View.Summary.class)
	public UUID getUuid();
	
	@JsonView(View.Summary.class)
	public String getName();

	public List<? extends AttributeDefinition> getAttributes();

}
