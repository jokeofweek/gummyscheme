package com.gummy.primitives;

import java.util.List;

import com.gummy.core.Compiler;
import com.gummy.core.Environment;
import com.gummy.types.Expression;
import com.gummy.types.Procedure;


/**
 * Evaluates the expression in the first argument.
 * 
 * (eval '(if #t 1 2)) => 1
 * 
 * @author Dominic Charley-Roy
 *
 */
public class Eval extends Procedure {

	/**
	 * 
	 */
	private static final long serialVersionUID = 559190249918304912L;

	@Override
	public Object apply(Environment environment, List<Object> arguments) {
		return Expression.eval(Expression.analyze(arguments.get(0)), environment);
	}

}
