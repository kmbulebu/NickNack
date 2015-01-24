package com.github.kmbulebu.nicknack.core.valuetypes;

import com.github.kmbulebu.nicknack.core.attributes.filters.Operator;


public class WholeNumberType extends AbstractValueType<Integer> {
	
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
	public Class<Integer> getTypeClass() {
		return Integer.class;
	}

	@Override
	public String getName() {
		return "number";
	}
	
	@Override
	public boolean isValid(Integer input) {
		if (input == null) {
			return false;
		}
		
		if (input < min) {
			return false;
		}
		
		if (input > max) {
			return false;
		}
		
		return true;
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

	@Override
	public String save(Object settingValue) {
		return getTypeClass().cast(settingValue).toString();
	}

	@Override
	public Integer load(String savedData) {
		return Integer.parseInt(savedData);
	}
	
	@Override
	public boolean evaluate(Operator operator, Integer operand1, Integer operand2) {
		switch(operator) {
		case EQUALS:
			return operand1.equals(operand2);
		default:
			return false;
		}
	}
	
	@Override
	public boolean evaluate(Operator operator, Integer operand1, Integer[] operand2) {
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
