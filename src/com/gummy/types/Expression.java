package com.gummy.types;

import java.io.Serializable;

import com.gummy.core.Environment;
import com.gummy.core.InterpreterException;
import com.gummy.core.Marshall;
import com.gummy.forms.Application;
import com.gummy.forms.Assignment;
import com.gummy.forms.Begin;
import com.gummy.forms.Definition;
import com.gummy.forms.If;
import com.gummy.forms.Lambda;
import com.gummy.forms.expansion.Let;
import com.gummy.forms.expansion.MacroDefinition;

/**
 * @author Dom
 * 
 */
public abstract class Expression implements Serializable {

	private static final long serialVersionUID = 332756773800670061L;

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
			return Variable.getVariable((Symbol) o);
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

			if (pair.getCar() instanceof Symbol) {
				Object form = getSpecialForm(pair.getCar(),
						Marshall.getPair(pair.getCdr()));
				if (form != null) {
					// If the form returned is an expansion, keep on expanding
					// until we reach a non-expansion
					while (form instanceof Expansion) {
						form = ((Expansion) form).expand(Marshall
								.getPair(pair.getCdr()));
					}
					return form;
				}
			}

			return new Application(analyzePair(pair));

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
			} else if (pair instanceof SplicedPair) {
				return new SplicedPair(analyze(((Pair) pair).getCar()),
						analyzePair(((Pair) pair).getCdr()));
			} else {
				return new Pair(analyze(((Pair) pair).getCar()),
						analyzePair(((Pair) pair).getCdr()));
			}
		} else {
			return analyze(pair);
		}
	}

	/**
	 * This evaluates a first symbol of a pair and the arguments, determining if
	 * it is a special form, and if so returning the special form expression.
	 * The car of the pair must be a symbol. *
	 * 
	 * @param car
	 *            The car of the initial pair, the symbol representing the form
	 * @param cdr
	 *            The remaining arguments
	 * @return The special form object if it matches any, or else null.
	 */
	public static Object getSpecialForm(Object car, Pair cdr) {
		if (car instanceof Symbol) {
			// Determine the function name from the symbol
			String form = ((Symbol) car).getSymbol();

			if ("define".equals(form)) {
				// Call define with a raw symbol / pair as the first argument
				// and then all other values as analyzed values.
				return new Definition(new Pair(cdr.getCar(),
						analyzePair(cdr.getCdr())));
			} else if ("set!".equals(form)) {
				// Call set! with a raw symbol / pair as the first argument
				// and then all other values as analyzed values.
				return new Assignment(new Pair(cdr.getCar(),
						analyzePair(cdr.getCdr())));
			} else if ("if".equals(form)) {
				// Pass all analyzed values to the If.
				return new If(analyzePair(cdr));
			} else if ("quote".equals(form)) {
				// Pass the first argument to the form and analyze it.
				return new Quote(cdr.getCar()).analyze();
			} else if ("begin".equals(form)) {
				// Pass all analyzed values to the Begin.
				return new Begin(analyzePair(cdr), false);
			} else if ("lambda".equals(form)) {
				// Pass the first argument as the bindings, and then
				// the analyzed arguments as the result.
				return new Lambda(cdr.getCar(),
						Marshall.getPair(analyzePair(cdr.getCdr())));
			} else if ("define-macro".equals(form)) {
				return MacroDefinition.getInstance();
			} else if ("let".equals(form)) {
				return Let.getInstance();
			}
		}
		return null;
	}

	/**
	 * This compares two objects, determining if they are equal or not.
	 * 
	 * @param obj1
	 * @param obj2
	 * @return True if the objects have the same reference (or are both null),
	 *         or if the {@link Object#equals(Object)} method of the first
	 *         parameter returns true for the second parameter.
	 */
	public static boolean equals(Object obj1, Object obj2) {
		if (obj1 == obj2)
			return Boolean.TRUE;

		if (obj1 == null || obj2 == null)
			return Boolean.FALSE;

		return (Marshall.getJavaObject(obj1).equals(Marshall
				.getJavaObject(obj2)));
	}

}
