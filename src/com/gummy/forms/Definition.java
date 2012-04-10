package com.gummy.forms;

import com.gummy.core.Environment;
import com.gummy.core.Marshall;
import com.gummy.types.Constant;
import com.gummy.types.Expression;
import com.gummy.types.Pair;

/**
 * This form defines a variable in a given environment with a specified value.
 * 
 * To define a simple variable: <code>
 * (define pi 3.14)
 * </code>
 * 
 * There is also syntactic sugar for creating a function: <code>
 * (define (add x y)
 *   (+ x y))
 * </code>
 * 
 * You can also create variadic functions like so: <code>
 * (define (name arg1 arg2 ... argn . rest)
 *   expr1
 *   expr2
 *   ...
 *   exprn)
 * </code>
 * 
 * And you can create entirely variadic functions like so: <code>
 * (define (name . args)
 *   expr1
 *   expr2
 *   ...
 *   exprn)
 * </code>
 * 
 * @author Dominic Charley-Roy
 * 
 */
public class Definition extends Expression {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5407087055188614410L;
	private Pair arguments;

	public Definition(Object arguments) {
		this.arguments = Marshall.getPair(arguments);
	}

	@Override
	public Object eval(Environment environment) {
		// Check for syntactic sugar for lambdas
		if (arguments.getCar() instanceof Pair) {
			Pair bindings = (Pair) arguments.getCar();
			environment
					.defineValue(Marshall.getSymbol(bindings.getCar()),
							Expression.eval(new Lambda(bindings.getCdr(),
									Marshall.getPair(arguments.getCdr())),
									environment));
		} else {
			environment.defineValue(Marshall.getSymbol(arguments.getCar()),
					Expression.eval(Marshall.getPair(arguments.getCdr())
							.getCar(), environment));
		}

		return Constant.UNSPECIFIED;
	}

}
