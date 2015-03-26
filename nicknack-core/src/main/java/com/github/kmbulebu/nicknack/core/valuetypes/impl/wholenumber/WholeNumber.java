package com.github.kmbulebu.nicknack.core.valuetypes.impl.wholenumber;

import com.github.kmbulebu.nicknack.core.attributes.filters.Operator;
import com.github.kmbulebu.nicknack.core.valuetypes.ValueType;
import com.github.kmbulebu.nicknack.core.valuetypes.impl.ResultBuilder;

public class WholeNumber implements ValueType<Integer> {
	
	private final Integer minValue;
	private final Integer maxValue;
	
	
	public WholeNumber(Integer minValue, Integer maxValue) {
		this.minValue = minValue;
		this.maxValue = maxValue;
	}
	
	public WholeNumber() {
		this(null, null);
	}

	@Override
	public Class<Integer> getValueClass() {
		return Integer.class;
	}

	@Override
	public String getName() {
		return "Whole Number";
	}

	@Override
	public String getIsValidRegEx() {
		return "-?\\d+";
	}
	
	@Override
	public ValueType.Result validate(Integer value) {
		final ResultBuilder resultBuilder = new ResultBuilder();
		if (minValue != null && minValue > value) {
			resultBuilder.withMessage("Must be at least " + minValue);
		}
		
		if (maxValue != null && maxValue < value) {
			resultBuilder.withMessage("Must be at most " + maxValue);
		}
		
		return resultBuilder.build();
	}
	
	public Integer getMaxValue() {
		return maxValue;
	}
	
	public Integer getMinValue() {
		return minValue;
	}

private static final Operator[] SUPPORTED_OPERATORS = { Operator.EQUALS, Operator.IN, Operator.NOT_IN };
	
	@Override
	public Operator[] getSupportedOperators() {
		return SUPPORTED_OPERATORS;
	}

	@Override
	public boolean evaluate(Integer value, Operator operator, Integer operand) {
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
	public boolean evaluateArray(Integer value, Operator operator, Integer[] operands) {
		boolean result;
		switch (operator) {
		case IN:
			boolean in = false;
			for (Integer operand : operands) {
				if (value.equals(operand)) {
					in = true;
					break;
				}
			}
			result = in;
			break;
		case NOT_IN:
			boolean notIn = true;
			for (Integer operand : operands) {
				if (value.equals(operand)) {
					notIn = false;
					break;
				}
			}
			result = notIn;
			break;
		default:
			result = false;
			break;
		
		}
		return result;
	}

	@Override
	public Integer fromString(String valueString) throws ParseException {
		if (valueString == null) {
			return null;
		} else {
			try {
				return Integer.parseInt(valueString);
			} catch (NumberFormatException e) {
				throw new ParseException("Value is not a whole number.", e);
			}
		}
	}

	@Override
	public String toString(Integer value) {
		if (value == null) {
			return null;
		} else {
			return value.toString();
		}
	}

	
}
