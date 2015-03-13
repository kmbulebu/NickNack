package com.github.kmbulebu.nicknack.core.valuetypes;



public class TextType extends AbstractValueType {
	
	protected int minimumLength = 0;
	protected int maximumLength = Integer.MAX_VALUE;
	protected String regexPattern = null;
	protected String regexErrorMessage = null;

	@Override
	public String getName() {
		return "text";
	}
	
	@Override
	public Validation validate(String input) {		
		if (input.length() < getMinimumLength()) {
			return buildInvalid("Value must be at least " + getMinimumLength() + " characters long.");
		}
		
		if (input.length() > getMaximumLength()) {
			return buildInvalid("Value must be at most " + getMaximumLength() + " characters long.");
		}
		
		if (getRegexPattern() != null && !input.matches(getRegexPattern())) {
			if (getRegexErrorMessage() == null) {
				return buildInvalid("Value did match regular expression pattern: " + getRegexPattern());
			} else {
				return buildInvalid(getRegexErrorMessage());
			}
		}
		
		return buildValid();
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
	
	public String getRegexErrorMessage() {
		return regexErrorMessage;
	}
	
	public void setRegexErrorMessage(String regexErrorMessage) {
		this.regexErrorMessage = regexErrorMessage;
	}

}
