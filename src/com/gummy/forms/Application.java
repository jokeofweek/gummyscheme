package com.gummy.forms;

import java.util.List;

import com.gummy.core.Environment;
import com.gummy.core.Marshall;
import com.gummy.types.Constant;
import com.gummy.types.Expression;
import com.gummy.types.Pair;
import com.gummy.types.Procedure;

/**
 * This form invokes the {@link Procedure#apply(Environment, List)} method of a
 * given procedure with a set of arguments, and is essentially calling the
 * procedure. The result of the evaluation is the result of the application.
 * 
 * The syntax: <code>
 * (proc arg1 arg2 ... argn)
 * </code>
 * 
 * The syntax for applying a method with no arguments: <code>
 * (proc)
 * </code>
 * 
 * The syntax for invoking an anonymous procedure: <code>
 * ((lambda (arg1 arg2 ... argn) ...) arg1-value arg2-value ... argn-value)
 * </code>
 * 
 * @author Dominic Charley-Roy
 * 
 */
public class Application extends Expression {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5407087055188614410L;
	private Object procedure;
	private List<Object> arguments;

	public Application(Object arguments) {
		Pair allArguments = Marshall.getPair(arguments);
		this.procedure = allArguments.getCar();
		this.arguments = Marshall.getPair(allArguments.getCdr()).expand();
	}

	/* (non-Javadoc)
	 * @see com.gummy.types.Expression#eval(com.gummy.core.Environment)
	 */
	@Override
	public Object eval(Environment environment) {
		return Marshall.getProcedure(Expression.eval(procedure, environment))
				.apply(environment, arguments);
	}

}
