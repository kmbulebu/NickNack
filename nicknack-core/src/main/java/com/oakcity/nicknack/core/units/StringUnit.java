package com.oakcity.nicknack.core.units;

import com.oakcity.nicknack.core.events.filters.operators.Operator;

public class StringUnit implements Unit {
	
	public static final StringUnit INSTANCE = new StringUnit();


	@Override
	public String getName() {
		return "Text";
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
