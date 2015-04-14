package com.github.kmbulebu.nicknack.server.restmodel;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonView;

public interface State extends StateDefinition {

	@JsonView(View.Summary.class)
	public List<Attribute> getAttributes();
	
}
