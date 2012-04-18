package com.gummy.primitives.predicates;

import java.util.Arrays;
import java.util.List;

import com.gummy.core.Environment;
import com.gummy.types.Expression;
import com.gummy.types.Procedure;

/**
 * This equality check takes more details into consideration then the
 * {@link Eqv} procedure. It makes use of the
 * {@link Expression#equals(Object, Object)} method.
 * 
 * @author Dominic Charley-Roy
 * 
 */
public class Equal extends Procedure {

	private static final long serialVersionUID = -271375514710336037L;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gummy.types.Procedure#apply(com.gummy.core.Environment,
	 * java.util.List)
	 */
	@Override
	public Object apply(Environment environment, List<Object> arguments) {
		// Get the two arguments
		return Expression.equals(
				Expression.eval(arguments.get(0), environment),
				Expression.eval(arguments.get(1), environment));

	}

}
