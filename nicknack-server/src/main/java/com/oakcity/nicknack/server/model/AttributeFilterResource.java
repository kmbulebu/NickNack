package com.oakcity.nicknack.server.model;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.hateoas.ResourceSupport;

import com.oakcity.nicknack.core.events.filters.EventFilter.AttributeFilter;
import com.oakcity.nicknack.core.events.filters.operators.Operator;

@Entity
@Table(name="AttributeFilters")
public class AttributeFilterResource extends ResourceSupport implements AttributeFilter {
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private UUID uuid;
	
	private UUID appliesToAttributeDefinition;
	
	@Enumerated(EnumType.STRING)
	private Operator operator;
	
	private String operand;

	@Override
	public UUID getAppliesToAttributeDefinition() {
		return appliesToAttributeDefinition;
	}

	@Override
	public Operator getOperator() {
		return operator;
	}

	@Override
	public String getOperand() {
		return operand;
	}

}
