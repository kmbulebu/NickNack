package com.github.kmbulebu.nicknack.core.valuetypes;

import com.github.kmbulebu.nicknack.core.attributes.filters.Operator;



public class TextType extends AbstractValueType<String> {
	
	protected int minimumLength = 0;
	protected int maximumLength = Integer.MAX_VALUE;
	protected String regexPattern = null;

	@Override
	public Class<String> getTypeClass() {
		return String.class;
	}

	@Override
	public String getName() {
		return "text";
	}
	
	@Override
	public boolean isValid(String input) {
		if (input == null) {
			return false;
		}
		
		if (input.length() < getMinimumLength()) {
			return false;
		}
		
		if (input.length() > getMaximumLength()) {
			return false;
		}
		
		if (getRegexPattern() != null && !input.matches(getRegexPattern())) {
			return false;
		}
		
		return true;
	}

	public int getMinimumLength() {
		return minimumLength;
	}

	public void setMinimumLength(int minimumLength) {
		this.minimumLength = minimumLength;
	}

	public int getMaximumLength() {
		return maximumLength;
	}

	public void setMaximumLength(int maximumLength) {
		this.maximumLength = maximumLength;
	}
	
	public String getRegexPattern() {
		return regexPattern;
	}
	
	public void setRegexPattern(String regexPattern) {
		this.regexPattern = regexPattern;
	}

	@Override
	public String save(Object settingValue) {
		return getTypeClass().cast(settingValue);
	}

	@Override
	public String load(String savedData) {
		return savedData;
	}

	@Override
	public boolean evaluate(Operator operator, String operand1, String operand2) {
		switch(operator) {
		case EQUALS:
			return operand1.equals(operand2);
		default:
			return false;
		}
	}
	
	@Override
	public boolean evaluate(Operator operator, String operand1, String[] operand2) {
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
