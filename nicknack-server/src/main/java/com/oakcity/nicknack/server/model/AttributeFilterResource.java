package com.oakcity.nicknack.server.model;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.oakcity.nicknack.core.events.filters.EventFilter;
import com.oakcity.nicknack.core.events.filters.EventFilter.AttributeFilter;
import com.oakcity.nicknack.core.events.filters.operators.Operator;

@Entity
@Table(name="AttributeFilters")
@Relation(value="AttributeFilter", collectionRelation="AttributeFilters")
public class AttributeFilterResource extends ResourceSupport implements AttributeFilter {
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private UUID uuid;
	
	private UUID appliesToAttributeDefinition;
	
	@Enumerated(EnumType.STRING)
	private Operator operator;
	
	private String operand;
	
	@ManyToOne(targetEntity=EventFilterResource.class)
	@JsonIgnore
	private EventFilter eventFilter;

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

	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	public EventFilter getEventFilter() {
		return eventFilter;
	}

	public void setEventFilter(EventFilter eventFilter) {
		this.eventFilter = eventFilter;
	}

	public void setAppliesToAttributeDefinition(UUID appliesToAttributeDefinition) {
		this.appliesToAttributeDefinition = appliesToAttributeDefinition;
	}

	public void setOperator(Operator operator) {
		this.operator = operator;
	}

	public void setOperand(String operand) {
		this.operand = operand;
	}
	
	

}
