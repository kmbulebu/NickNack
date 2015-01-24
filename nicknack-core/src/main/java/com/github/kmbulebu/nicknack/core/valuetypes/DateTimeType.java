package com.github.kmbulebu.nicknack.core.valuetypes;

import java.util.Date;

import com.github.kmbulebu.nicknack.core.attributes.filters.Operator;

public class DateTimeType extends AbstractValueType<Date> {

	@Override
	public Class<Date> getTypeClass() {
		return Date.class;
	}

	@Override
	public String getName() {
		return "datetime";
	}

	@Override
	public boolean isValid(Date input) {
		return true;
	}

	@Override
	public String save(Object settingValue) {
		return Long.toString(Date.class.cast(settingValue).getTime());
	}

	@Override
	public Date load(String savedData) {
		return new Date(Long.parseLong(savedData));
	}
	
	@Override
	public boolean evaluate(Operator operator, Date operand1, Date operand2) {
		switch(operator) {
		case EQUALS:
			return operand1.equals(operand2);
		default:
			return false;
		}
	}
	
	@Override
	public boolean evaluate(Operator operator, Date operand1, Date[] operand2) {
		switch(operator) {
		case IN:
			return evaluateInOperand(operand1, operand2);
		case NOT_IN:
			return evaluateNotInOperand(operand1, operand2);
		default:
			return false;
		}
	}

	@Override
	public Operator[] getSupportedOperators() {
		return new Operator[] {Operator.EQUALS, Operator.IN, Operator.NOT_IN};
	}

}
