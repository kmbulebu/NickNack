package com.github.kmbulebu.nicknack.core.valuetypes.impl.booleans;

import com.github.kmbulebu.nicknack.core.attributes.filters.Operator;
import com.github.kmbulebu.nicknack.core.valuetypes.ValueType;
import com.github.kmbulebu.nicknack.core.valuetypes.impl.ResultBuilder;

public class OnOff implements ValueType<Boolean> {
	
	private static final Operator[] SUPPORTED_OPERATORS = { Operator.EQUALS };

	@Override
	public Class<Boolean> getValueClass() {
		return Boolean.class;
	}

	@Override
	public String getName() {
		return "On/Off";
	}

	@Override
	public String getIsValidRegEx() {
		return "(on|off|On|Off)";
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
		} else if ("On".equalsIgnoreCase(valueString)) {
			return Boolean.TRUE;
		} else if ("Off".equalsIgnoreCase(valueString)) {
			return Boolean.FALSE;
		} else {
			throw new ParseException("Value was not one of 'On' or 'Off'.");
		}
	}

	@Override
	public String toString(Boolean value) {
		if (value == null) {
			return null;
		} else if (Boolean.TRUE.equals(value)) {
			return "On";
		} else {
			return "Off";
		}
	}

}
