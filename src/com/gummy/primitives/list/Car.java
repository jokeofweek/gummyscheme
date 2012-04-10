package com.gummy.primitives.list;

import java.util.List;

import com.gummy.core.Environment;
import com.gummy.core.InterpreterException;
import com.gummy.core.Marshall;
import com.gummy.types.Expression;
import com.gummy.types.Pair;
import com.gummy.types.Procedure;

/**
 * This procedure returns the car (head element) of a pair.
 * 
 * @author Dominic Charley-Roy
 * 
 */
public class Car extends Procedure {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gummy.types.Procedure#apply(com.gummy.core.Environment,
	 * java.util.List)
	 */
	@Override
	public Object apply(Environment environment, List<Object> arguments) {
		Pair p = Marshall
				.getPair(Expression.eval(arguments.get(0), environment));

		if (p == Pair.EMPTY_LIST)
			throw new InterpreterException(
					"Cannot get the car of the empty list.");

		return p.getCar();
	}

}
