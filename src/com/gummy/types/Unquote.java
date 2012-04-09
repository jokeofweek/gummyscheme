package com.gummy.types;

/**
 * This evaluation form is meant to be used within a {@link Quasiquote} form and
 * allows interpolation of evaluated values in a quoted value,.
 * 
 * @author Dominic Charley-Roy
 * 
 */
public class Unquote implements AnalyzedForm {

	private Object expression;

	public Unquote(Object expression) {
		this.expression = expression;
	}

	@Override
	public Object analyze() {
		return Expression.analyze(expression);
	}

}
