package com.gummy.types;

/**
 * This form provides a container for an expression which simply returns the
 * original expression when analyzed. This 'quotes' data.
 * 
 * @author Dominic Charley-Roy
 * 
 */
public class Quote implements AnalyzedForm {

	private Object expression;

	public Quote(Object expression) {
		this.expression = expression;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gummy.types.AnalyzedForm#analyze()
	 */
	@Override
	public Object analyze() {
		return expression;
	}

}
