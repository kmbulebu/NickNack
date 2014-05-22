package com.oakcity.nicknack.core.events.attributes.units;

import com.oakcity.nicknack.core.Unit;
import com.oakcity.nicknack.core.events.filters.operators.Operator;

public class StringUnit implements Unit<String> {

	@Override
	public int compare(String o1, String o2) {
		return o1.compareTo(o2);
	}

	@Override
	public String getName() {
		return "";
	}

	@Override
	public Class<String> getType() {
		return String.class;
	}

	@Override
	public String getMin() {
		return null; // unbounded
	}

	@Override
	public String getMax() {
		return null; // unbounded
	}

	@Override
	public String parse(String input) {
		return new String(input);
	}

	@Override
	public String toString(String input) {
		return new String(input);
	} 
	
	@Override
	public Boolean evaluate(Operator operator, String operand1, String operand2) {
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
