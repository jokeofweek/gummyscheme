package com.gummy.primitives.list;

import java.util.List;

import com.gummy.core.Environment;
import com.gummy.types.Expression;
import com.gummy.types.Pair;
import com.gummy.types.Procedure;

/**
 * This procedure simply creates a pair based on two objects.
 * 
 * @author Dominic Charley-Roy
 * 
 */
public class Cons extends Procedure {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gummy.types.Procedure#apply(com.gummy.core.Environment,
	 * java.util.List)
	 */
	@Override
	public Object apply(Environment environment, List<Object> arguments) {
		return new Pair(Expression.eval(arguments.get(0), environment),
				Expression.eval(arguments.get(1), environment));
	}

}
