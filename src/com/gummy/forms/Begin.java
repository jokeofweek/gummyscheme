package com.gummy.forms;

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

	/**
	 * 
	 */
	private static final long serialVersionUID = 8042953803317300911L;
	public List<Object> expressions;
	public boolean extendEnvironment;

	/**
	 * This wraps a set of expressions in a Begin clause and also allows you to
	 * specify that the environment should be extended before evaluating.
	 * 
	 * @param arguments
	 *            The expressions that make up the Begin.
	 * @param extendEnvironment
	 *            If this is true, an extension of the environment will be
	 *            created upon evaluation and will be used as context.
	 */
	public Begin(Object arguments, boolean extendEnvironment) {
		this.expressions = Marshall.getPair(arguments).expand();
		this.extendEnvironment = extendEnvironment;
	}

	/**
	 * This wraps a set of expressions in a Begin clause and also allows you to
	 * specify that the environment should be extended before evaluating.
	 * 
	 * @param expressions
	 *            The expressions that make up the Begin.
	 * @param extendEnvironment
	 *            If this is true, an extension of the environment will be
	 *            created upon evaluation and will be used as context.
	 */
	public Begin(List<Object> expressions, boolean extendEnvironment) {
		this.expressions = expressions;
		this.extendEnvironment = extendEnvironment;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gummy.types.Expression#eval(com.gummy.core.Environment)
	 */
	@Override
	public Object eval(Environment environment) {
		// Extend the environment if necessary.
		if (extendEnvironment)
			environment = new Environment(environment);

		// Evaluate all expressions apart the last one in the current scope
		for (int i = 0; i < expressions.size() - 1; i++)
			Expression.eval(expressions.get(i), environment);

		// Evaluate and return the last expression
		return Expression.eval(expressions.get(expressions.size() - 1),
				environment);
	}

}
