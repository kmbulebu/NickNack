package com.oakcity.nicknack.core.events.attributes.units;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.oakcity.nicknack.core.Unit;
import com.oakcity.nicknack.core.events.filters.operators.Operator;

public class DateTimeUnit implements Unit<Date> {
	
	public static final DateTimeUnit INSTANCE = new DateTimeUnit();

	@Override
	public int compare(Date o1, Date o2) {
		return o1.compareTo(o2);
	}

	@Override
	public String getName() {
		return "Date and Time";
	}

	@Override
	public Class<Date> getType() {
		return Date.class;
	}

	@Override
	public Date getMin() {
		return null; // unbounded
	}

	@Override
	public Date getMax() {
		return null; // unbounded
	}

	@Override
	public Date parse(String input) throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat();
		return dateFormat.parse(input);
	}

	@Override
	public String toString(Date input) {
		SimpleDateFormat dateFormat = new SimpleDateFormat();
		return dateFormat.format(input);
	} 
	
	@Override
	public Boolean evaluate(Operator operator, Date operand1, Date operand2) {
		switch(operator) {
		case EQUALS:
			return operand1.equals(operand2);
		case EVERY:
			return (operand1.getTime() % operand2.getTime()) == 0;
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
