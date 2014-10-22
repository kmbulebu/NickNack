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
		case IN:
			final String[] split = operand2.split(",");
			for (String token : split) {
				final String cleanToken = token.trim();
				if (operand1.equals(cleanToken)) {
					return true;
				}
			}
			return false;
		case NOT_IN:
			final String[] split2 = operand2.split(",");
			for (String token : split2) {
				final String cleanToken = token.trim();
				if (operand1.equals(cleanToken)) {
					return false;
				}
			}
			return true;
		default:
			return null;
			//throw new UnsupportedOperationException(operator.getName() + " operator is not supported by " + getName());
		}
	} 
	
	@Override
	public Operator[] getSupportedOperators() {
		return new Operator[] {Operator.EQUALS, Operator.IN, Operator.NOT_IN};
	}

}
