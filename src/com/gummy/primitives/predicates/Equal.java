package com.gummy.primitives.predicates;

import java.util.Arrays;
import java.util.List;

import com.gummy.core.Environment;
import com.gummy.types.Expression;
import com.gummy.types.Procedure;

/**
 * This equality check takes more details into consideration then the
 * {@link Eqv} procedure. It makes use of the {@link Object#equals(Object)}
 * checks.
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
		Object first = Expression.eval(arguments.get(0), environment);
		Object second = Expression.eval(arguments.get(1), environment);

		// Pointer comparison
		if (first == second)
			return Boolean.TRUE;

		// Null comparison to prevent error. We can exit early here with a false
		// as we know that they are not equal references.
		if (first == null || second == null)
			return Boolean.FALSE;

		// If they are strings (char arrays), we must convert them to strings.
		if (first instanceof char[] && second instanceof char[])
			return new String((char[]) first).equals(new String((char[]) second));
		
		// Value comparison
		return first.equals(second);
	}

}
