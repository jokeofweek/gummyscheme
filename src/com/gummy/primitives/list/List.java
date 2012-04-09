package com.gummy.primitives.list;

import com.gummy.core.Environment;
import com.gummy.types.Expression;
import com.gummy.types.Pair;
import com.gummy.types.Procedure;

/**
 * This procedure simply creates a list based on 1 or more objects.
 * 
 * @author Dominic Charley-Roy
 * 
 */
public class List extends Procedure {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gummy.types.Procedure#apply(com.gummy.core.Environment,
	 * java.util.List)
	 */
	@Override
	public Object apply(Environment environment,
			java.util.List<Object> arguments) {
		// If no arguments, return empty list
		if (arguments.size() == 0)
			return Pair.EMPTY_LIST;

		// Start with the empty pair
		Pair current = Pair.EMPTY_LIST;

		// Iterate backwards through the arguments, building the list.
		for (int i = arguments.size() - 1; i >= 0; i--) {
			current = new Pair(Expression.eval(arguments.get(i), environment),
					current);
		}

		return current;
	}

}
