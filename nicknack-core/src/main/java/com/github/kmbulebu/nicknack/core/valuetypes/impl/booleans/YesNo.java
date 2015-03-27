package com.github.kmbulebu.nicknack.core.valuetypes.impl.booleans;

import com.github.kmbulebu.nicknack.core.attributes.filters.Operator;
import com.github.kmbulebu.nicknack.core.valuetypes.ValueType;
import com.github.kmbulebu.nicknack.core.valuetypes.impl.ResultBuilder;

public class YesNo implements ValueType<Boolean> {
	
	private static final Operator[] SUPPORTED_OPERATORS = { Operator.EQUALS };

	@Override
	public Class<Boolean> getValueClass() {
		return Boolean.class;
	}

	@Override
	public String getName() {
		return "Yes/No";
	}

	@Override
	public String getIsValidRegEx() {
		return "(yes|no|Yes|No)";
	}
	
	@Override
	public Operator[] getSupportedOperators() {
		return SUPPORTED_OPERATORS;
	}

	@Override
	public boolean evaluate(Boolean value, Operator operator, Boolean operand) {
		boolean result;
		switch (operator) {
		case EQUALS:
			result = value.equals(operand);
		default:
			result = false;
			break;
		
		}
		return result;
	}

	@Override
	public boolean evaluateArray(Boolean value, Operator operator, Boolean[] operands) {
		return false;
	}

	@Override
	public ValueType.Result validate(Boolean value) {
		return new ResultBuilder().build();
	}

	@Override
	public Boolean fromString(String valueString)
			throws ParseException {
		if (valueString == null) {
			return null;
		} else if ("Yes".equalsIgnoreCase(valueString)) {
			return Boolean.TRUE;
		} else if ("No".equalsIgnoreCase(valueString)) {
			return Boolean.FALSE;
		} else {
			throw new ParseException("Value was not one of 'Yes' or 'No'.");
		}
	}

	@Override
	public String toString(Boolean value) {
		if (value == null) {
			return null;
		} else if (Boolean.TRUE.equals(value)) {
			return "Yes";
		} else {
			return "No";
		}
	}

}
