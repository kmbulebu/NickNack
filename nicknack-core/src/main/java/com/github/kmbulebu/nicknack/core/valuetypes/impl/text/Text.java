package com.github.kmbulebu.nicknack.core.valuetypes.impl.text;

import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import com.github.kmbulebu.nicknack.core.attributes.filters.Operator;
import com.github.kmbulebu.nicknack.core.valuetypes.ValueType;
import com.github.kmbulebu.nicknack.core.valuetypes.impl.ResultBuilder;

public class Text implements ValueType<String> {
	
	private static final Operator[] SUPPORTED_OPERATORS = { Operator.EQUALS, Operator.IN, Operator.NOT_IN };
	
	private final Integer minLength;
	private final Integer maxLength;
	private final String regex;
	private final String regexNotMatchMessage;
	
	public Text(Integer minLength, Integer maxLength, String regex, String regexNotMatchMessage) {
		this.minLength = minLength;
		this.maxLength = maxLength;
		this.regex = regex;
		this.regexNotMatchMessage = regexNotMatchMessage;
	}
	
	public Text() {
		this(null, null, null, null);
	}

	@Override
	public Class<String> getValueClass() {
		return String.class;
	}

	@Override
	public String getName() {
		return "Text";
	}

	@Override
	public String getIsValidRegEx() {
		return ".*";
	}
	

	@Override
	public ValueType.Result validate(String value) {
		final ResultBuilder resultBuilder = new ResultBuilder();
		if (minLength != null && minLength > value.length()) {
			resultBuilder.withMessage("Must be at least " + minLength + " characters.");
		}
		
		if (maxLength != null && maxLength < value.length()) {
			resultBuilder.withMessage("Must be at most " + maxLength + " characters.");
		}
		
		if (regex != null) {
			Pattern pattern = null;
			
			try {
				pattern = Pattern.compile(regex);
			} catch (PatternSyntaxException e) {
				resultBuilder.withMessage("Regex is invalid. Please contact developer.");
			}
			
			if (pattern != null) {
				if (!pattern.matcher(value).matches()) {
					if (regexNotMatchMessage == null) {
						resultBuilder.withMessage("Must match regex " + regex);
					} else {
						resultBuilder.withMessage(regexNotMatchMessage);
					}
				}
			}
		}
		
		return resultBuilder.build();
	}
	
	@Override
	public Operator[] getSupportedOperators() {
		return SUPPORTED_OPERATORS;
	}

	@Override
	public boolean evaluate(String value, Operator operator, String operand) {
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
	public boolean evaluateArray(String value, Operator operator, String[] operands) {
		boolean result;
		switch (operator) {
		case IN:
			boolean in = false;
			for (String operand : operands) {
				if (value.equals(operand)) {
					in = true;
					break;
				}
			}
			result = in;
			break;
		case NOT_IN:
			boolean notIn = true;
			for (String operand : operands) {
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

	public Integer getMinLength() {
		return minLength;
	}

	public Integer getMaxLength() {
		return maxLength;
	}

	public String getRegex() {
		return regex;
	}

	public String getRegexNotMatchMessage() {
		return regexNotMatchMessage;
	}

	@Override
	public String fromString(String valueString)
			throws ParseException {
		if (valueString == null) {
			return null;
		} else {
			return valueString;
		}
	}

	@Override
	public String toString(String value) {
		if (value == null) {
			return null;
		} else {
			return value;
		}
	}
	
	

}
