package com.oakcity.nicknack.core.units;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

import com.oakcity.nicknack.core.events.filters.operators.Operator;

public class DateTimeUnit implements Unit {
	
	public static final DateTimeUnit INSTANCE = new DateTimeUnit();

	@Override
	public String getName() {
		return "Date and Time";
	}
	
	@Override
	public Boolean evaluate(Operator operator, String operand1, String operand2) throws ParseException {
		// TODO Think hard about dates, times, and lengths of time.
		final DateFormat dateFormat = DateFormat.getDateTimeInstance();
		final Date op1 = dateFormat.parse(operand1);
		final Date op2 = dateFormat.parse(operand2);
		switch(operator) {
		case EQUALS:
			return op1.equals(op2);
		case EVERY:
			return (op1.getTime() % op2.getTime()) == 0;
		default:
			return null;
			//throw new UnsupportedOperationException(operator.getName() + " operator is not supported by " + getName());
		}
	} 
	
	@Override
	public Operator[] getSupportedOperators() {
		return new Operator[] {Operator.EQUALS, Operator.EVERY};
	}

}
