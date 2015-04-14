package com.github.kmbulebu.nicknack.server.restmodel;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.github.kmbulebu.nicknack.server.restmodel.impl.AttributeWithoutDefinitionImpl;

@JsonDeserialize(as=AttributeWithoutDefinitionImpl.class)
public interface Attribute extends AttributeDefinition {
	
	@JsonView(View.Summary.class)
	public String[] getValues();

}
