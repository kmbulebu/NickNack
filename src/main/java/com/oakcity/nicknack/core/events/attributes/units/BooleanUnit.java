package com.oakcity.nicknack.core.events.attributes.units;

import com.oakcity.nicknack.core.Unit;
import com.oakcity.nicknack.core.events.filters.operators.Operator;

public class BooleanUnit implements Unit<Boolean> {

	@Override
	public int compare(Boolean o1, Boolean o2) {
		return o1.compareTo(o2);
	}

	@Override
	public String getName() {
		return "";
	}

	@Override
	public Class<Boolean> getType() {
		return Boolean.class;
	}

	@Override
	public Boolean getMin() {
		return Boolean.FALSE;
	}

	@Override
	public Boolean getMax() {
		return Boolean.TRUE;
	}

	@Override
	public Boolean parse(String input) {
		return Boolean.parseBoolean(input);
	}

	@Override
	public String toString(Boolean input) {
		return input.toString();
	}

	@Override
	public Boolean evaluate(Operator operator, Boolean operand1, Boolean operand2) {
		switch(operator) {
		case EQUALS:
			return operand1.equals(operand2);
		default:
			return null;
			//throw new UnsupportedOperationException(operator.getName() + " operator is not supported by " + getName());
		}
	}

	@Override
	public Operator[] getSupportedOperators() {
		return new Operator[] {Operator.EQUALS};
	} 

}
