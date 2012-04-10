package com.gummy.forms;

import java.util.List;

import com.gummy.core.Environment;
import com.gummy.core.Marshall;
import com.gummy.types.Expression;
import com.gummy.types.Pair;
import com.gummy.types.SimpleCompoundProcedure;
import com.gummy.types.Symbol;
import com.gummy.types.VariadicCompoundProcedure;

/**
 * This form defines an anonymous function. Note that the scope is lexical, and
 * therefore a new scope is created within the lambda based on the scope at the
 * time of creation.
 * 
 * To create a basic lambda with a fixed number of arguments, the syntax is:
 * <code>
 * (lambda (arg1 arg2 ... argn)
 *   expr1
 *   expr2
 *   ...
 *   exprn)
 * </code>
 * 
 * Note that passing an empty list creates a procedure with no arguments.
 * 
 * To create a lambda with a fixed number of argument and then a variadic last
 * argument, simply denote the last argument as a pair rather then a list, like
 * so: <code>
 * (lambda (arg1 arg2 ... argn . rest)
 *   expr1
 *   expr2
 *   ...
 *   exprn)
 * </code>
 * 
 * In this case, the rest argument will contain either an empty list or all the
 * extra arguments.
 * 
 * To create a lambda which accepts any number of arguments, simply pass a
 * symbol rather then a list as the arguments, for example: <code>
 * (lambda args
 *   expr1
 *   expr2
 *   ...
 *   exprn)
 * </code>
 * 
 * @author Dominic Charley-Roy
 * 
 */
public class Lambda extends Expression {

	private static final long serialVersionUID = 619680549092236699L;

	private Pair bindings;
	private Pair expressions;
	private boolean variadic;

	public Lambda(Object bindings, Pair expressions) {

		// Determine if it is variadic based on whether the binding
		// is a Symbol, or it is a pair and not a list.
		if (bindings instanceof Symbol) {
			this.variadic = true;
			this.bindings = new Pair(bindings, Pair.EMPTY_LIST);
		} else {
			this.bindings = Marshall.getPair(bindings);
			// Iterate through the bindings to determine if it is variadic or
			// not.
			Pair pair = this.bindings;
			while (true) {
				if (pair == Pair.EMPTY_LIST) {
					this.variadic = false;
					break;
				} else if (pair.getCdr() instanceof Pair) {
					pair = (Pair) pair.getCdr();
				} else {
					this.variadic = true;
					break;
				}
			}
		}
		this.expressions = expressions;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gummy.types.Expression#eval(com.gummy.core.Environment)
	 */
	@Override
	public Object eval(Environment environment) {

		return variadic ? new VariadicCompoundProcedure(bindings, expressions,
				environment) : new SimpleCompoundProcedure(bindings,
				expressions, environment);
	}

}
