package com.gummy.primitives;

import java.util.List;

import com.gummy.core.Environment;
import com.gummy.core.Marshall;
import com.gummy.types.Expression;
import com.gummy.types.Pair;

/**
 * This form evaluates a list of expressions, returning the value of the last
 * one. Note that it evalutes it in the current environment and does not create
 * a new environment.
 * 
 * For example:
 * 
 * <code>
 * (begin
 *   (define a 10)
 *   a
 *   (set! a 43)
 * 	 a)
 * </code><br/>
 * 
 * The following code evaluates each expression sequentially, and returns the
 * value of the last one, in this case 43.
 * 
 * @author Dominic Charley-Roy
 * 
 */
public class Begin extends Expression {

	public List<Object> expressions;

	public Begin(Object arguments) {
		this.expressions = Pair.expand(Marshall.getPair(arguments));
	}

	/* (non-Javadoc)
	 * @see com.gummy.types.Expression#eval(com.gummy.core.Environment)
	 */
	@Override
	public Object eval(Environment environment) {
		// Evaluate all expressions apart the last one in the current scope
		for (int i = 0; i < expressions.size() - 1; i++)
			Expression.eval(expressions.get(i), environment);

		// Evaluate and return the last expression
		return Expression.eval(expressions.get(expressions.size() - 1),
				environment);
	}

}
