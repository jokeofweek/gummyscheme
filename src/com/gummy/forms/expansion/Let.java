package com.gummy.forms.expansion;

import java.util.List;

import com.gummy.core.Marshall;
import com.gummy.forms.Application;
import com.gummy.forms.Begin;
import com.gummy.forms.Definition;
import com.gummy.forms.Lambda;
import com.gummy.types.Expansion;
import com.gummy.types.Expression;
import com.gummy.types.Pair;
import com.gummy.types.Symbol;
import com.gummy.types.Variable;

public class Let extends Expansion {

	private static final long serialVersionUID = 7354434701503143380L;
	private static Let instance = null;

	private Let() {
	};

	/**
	 * Fetches the singleton instance of the expander.
	 * 
	 * @return The singleton instance of the expansion.
	 */
	public static Let getInstance() {
		if (instance == null) {
			instance = new Let();
		}

		return instance;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gummy.types.Expansion#expand(com.gummy.types.Pair)
	 */
	@Override
	public Object expand(Pair expressions) {
		Symbol name = null;

		// Check for a symbol if it is a named let
		if (expressions.getCar() instanceof Symbol) {
			name = Marshall.getSymbol(expressions.getCar());
			expressions = (Pair) expressions.getCdr();
		}

		List<Object> bindings = Marshall.getPair(expressions.getCar()).expand();

		// Iterate through bindings, taking out values and symbols
		Pair bindingsPair = Pair.EMPTY_LIST;
		Pair argsPair = Pair.EMPTY_LIST;
		Pair current;

		// Note that we are looping backwards through the bindings,
		// which is okay for a simple let as arg order does not
		// matter.
		for (Object o : bindings) {
			current = Marshall.getPair(o);
			bindingsPair = new Pair(current.getCar(), bindingsPair);
			argsPair = new Pair(Expression.analyze(((Pair) current.getCdr())
					.getCar()), argsPair);
		}

		// Process differently for named let and regular let
		if (name == null) {
			return new Application(new Pair(new Lambda(bindingsPair,
					Marshall.getPair(Expression.analyzePair(expressions
							.getCdr()))), argsPair));
		} else {
			// Create the procedure containing the expressions
			Lambda lambda = new Lambda(bindingsPair,
					Marshall.getPair(Expression.analyzePair(expressions
							.getCdr())));
			// Create the definition for the procedure
			Definition definition = new Definition(new Pair(name, new Pair(
					lambda, Pair.EMPTY_LIST)));

			// Create a Begin which will extend the environment, define the
			// expanded procedure and then apply it.
			return new Begin(new Pair(definition, new Application(new Pair(
					Variable.getVariable(name), argsPair))), true);
		}
	}

}
