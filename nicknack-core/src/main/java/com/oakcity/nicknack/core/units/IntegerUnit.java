package com.oakcity.nicknack.core.units;

import com.oakcity.nicknack.core.events.filters.operators.Operator;

public class IntegerUnit implements Unit {
	
	public static final IntegerUnit INSTANCE = new IntegerUnit();

	@Override
	public String getName() {
		return "Integer";
	}

	@Override
	public Boolean evaluate(Operator operator, String operand1, String operand2) {
		final Integer op1 = Integer.parseInt(operand1);
		final Integer op2 = Integer.parseInt(operand2);
		switch(operator) {
		case EQUALS:
			return op1.equals(op2);
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
