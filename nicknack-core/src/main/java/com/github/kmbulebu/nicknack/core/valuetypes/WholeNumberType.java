package com.github.kmbulebu.nicknack.core.valuetypes;


public class WholeNumberType extends AbstractValueType {
	
	private int min = Integer.MIN_VALUE;
	private int max = Integer.MAX_VALUE;
	private int step = 1;

	public WholeNumberType(int min, int max, int step) {
		super();
		this.min = min;
		this.max = max;
		this.step = step;
	}


	@Override
	public String getName() {
		return "number";
	}
	
	@Override
	public String getRegexPattern() {
		return "\\d+";
	}
	
	@Override
	public Validation validate(String input) {
		final Integer intValue = Integer.parseInt(input);
		
		if (intValue < min) {
			return buildInvalid("Value must be at least " + min + ".");
		}
		
		if (intValue > max) {
			return buildInvalid("Value must be at most " + max + ".");
		}
		
		if (intValue % getStep() != 0) {
			return buildInvalid("Value must be divisible by " + getStep() + ".");
		}
		
		return buildValid();
	}

	public int getMin() {
		return min;
	}

	public void setMin(int min) {
		this.min = min;
	}

	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
	}
	
	public int getStep() {
		return step;
	}
	
	public void setStep(int step) {
		this.step = step;
	}

}
