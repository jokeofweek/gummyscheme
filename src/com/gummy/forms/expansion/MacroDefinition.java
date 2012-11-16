package com.gummy.forms.expansion;

import com.gummy.forms.Definition;
import com.gummy.types.Expansion;
import com.gummy.types.Expression;
import com.gummy.types.Pair;

/**
 * This expansion is similar to the <i>define-macro</i> special form in Gambit
 * Scheme and allows you to define a macro based on a list of arguments needed
 * and a template.
 * 
 * Syntax:
 * 
 * (define-macro (name [arg1] [arg2] ... [argn]) template)
 * 
 * Note that the argument listing also permits variadic arguments in the same
 * style as the syntactic sugar for creating a function through the
 * {@link Definition} form.
 * 
 * @author Dominic Charley-Roy
 */
public class MacroDefinition extends Expansion {

	private static final long serialVersionUID = -426130756147564691L;
	private static MacroDefinition instance = null;

	private MacroDefinition() {
	};

	/**
	 * Fetches the singleton instance of the expander.
	 * 
	 * @return The singleton instance of the expansion.
	 */
	public static MacroDefinition getInstance() {
		if (instance == null) {
			instance = new MacroDefinition();
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
		Object template = Expression.analyzePair(Expression
				.analyzePair(expressions.getCdr()));
		return new Definition(new Pair(expressions.getCar(), template));
	}

}
