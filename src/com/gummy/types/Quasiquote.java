package com.gummy.types;

/**
 * This is similar to the {@link Quote} form however it supports interpolation
 * of values using {@link Unquote}.
 * 
 * @author Dominic Charley-Roy
 * 
 */
public class Quasiquote implements AnalyzedForm {

	private Object expression;

	public Quasiquote(Object expression) {
		this.expression = expression;
	}

	@Override
	public Object analyze() {
		return analyzeObject(expression);
	}

	/**
	 * This method analyzes a given object, evaluating any {@link Unquote} and
	 * {@link Pair} objects which belong to this quasiquoted value (and not a
	 * child quote or unquoted value).
	 * 
	 * @param o
	 *            The object to analyze
	 * @return The analyzed object
	 */
	private Object analyzeObject(Object o) {
		// Do a case by case analysis. The only forms that should be
		// analyzed are pairs and Unquote objects.
		if (o instanceof Unquote) {
			return ((Unquote) o).analyze();
		} else if (o instanceof Pair) {
			// If is the empty pair simply return it
			if (o == Pair.EMPTY_LIST)
				return o;

			// If it is a regular pair, analyze the car and the cdr.
			return new Pair(analyzeObject(((Pair) o).getCar()),
					analyzeObject(((Pair) o).getCdr()));
		} else {
			// Simply return the object (as if it was quoted)
			return o;
		}
	}

}
