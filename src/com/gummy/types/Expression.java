package com.gummy.types;

import com.gummy.core.Environment;
import com.gummy.core.InterpreterException;
import com.gummy.core.Marshall;
import com.gummy.primitives.Definition;
import com.gummy.primitives.Variable;

public abstract class Expression {

	/**
	 * This evaluates the expression within a given environment.
	 * 
	 * @param environment
	 *            The environment to use as context.
	 * @return The result of the evaluation.
	 */
	public abstract Object eval(Environment environment);

	/**
	 * This evaluates a given object and returning the evaluated version.
	 * 
	 * @param value
	 *            The value to evaluate.
	 * @param environment
	 *            The environment to evaluate in.
	 * @return The evaluated value.
	 */
	public static Object eval(Object value, Environment environment) {
		if (value instanceof Expression)
			return ((Expression) value).eval(environment);
		else
			return value;
	}

	/**
	 * Perform a type analysis in order to convert an object into the
	 * appropriate value. For example, converts a {@link Symbol} to a
	 * {@link Variable}.
	 * 
	 * @param o
	 *            The object to analyzed.
	 * @return The analyzed version of the object.
	 */
	public static Object analyze(Object o) {
		if (o == null)
			throw new InterpreterException(
					"Internal analysis error - null passed to analyze.");

		// Case by case analysis
		if (o instanceof Symbol) {
			// Symbol -> Variable
			return new Variable((Symbol) o);
		} else if (o instanceof AnalyzedForm) {
			// AnalyzedForm -> result of analyze
			return ((AnalyzedForm) o).analyze();
		} else if (o instanceof Pair) {
			// Special case for empty lists
			if (o == Pair.EMPTY_LIST) {
				return o;
			}

			// Analyze the car of the pair, and if it was a symbol first check
			// the special forms. If it was a special form, pass the raw
			// expression as arguments, or else create an application.
			Pair pair = (Pair) o;
			Object car = analyze(pair.getCar());

			if (car instanceof Expression) {
				// If a special form was found, return it.
				Object form = getSpecialForm(car,
						Marshall.getPair(pair.getCdr()));
				if (form != null) {
					return form;
				}
				// return new application
				return null;
			} else {
				return new Pair(car, analyzePair(pair.getCdr()));
			}

		} else {
			return o;
		}
	}

	/**
	 * This will recursively analyze each element of a pair/list.
	 * 
	 * @param pair
	 *            The pair to analyze.
	 * @return The analyzed version of the pair (the original pair will not be
	 *         modified).
	 */
	public static Object analyzePair(Object pair) {
		// If it is a pair, we must recurse, or else just analyze it
		if (pair instanceof Pair) {
			if (pair == Pair.EMPTY_LIST) {
				return Pair.EMPTY_LIST;
			} else {
				return new Pair(analyze(((Pair) pair).getCar()),
						analyzePair(((Pair) pair).getCdr()));
			}
		} else {
			return analyze(pair);
		}
	}

	/**
	 * This evaluates a pair, determining if it is a special form, and if so
	 * returning the special form expression. The car of the pair must be a
	 * symbol. The pair expressions should not have been analyzed yet.
	 * 
	 * @param pair
	 *            The pair object.
	 * @return The special form object if it matches any, or else null.
	 */
	public static Object getSpecialForm(Object car, Pair cdr) {
		if (car instanceof Variable) {
			// Determine the function name from the symbol
			String form = ((Variable) car).getSymbol().getSymbol();

			if ("define".equals(form)) {
				// Call define with a raw symbol / pair as the first argument
				// and then all other values as analyzed values.
				return new Definition(new Pair(cdr.getCar(),
						analyzePair(cdr.getCdr())));
			}
		}
		return null;
	}

}