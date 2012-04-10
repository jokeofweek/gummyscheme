package com.gummy.primitives.predicates;

import java.util.List;

import com.gummy.core.Environment;
import com.gummy.types.Expression;
import com.gummy.types.Procedure;

/**
 * This equality test obeys the following truth rules:
 * <ul>
 * <li>obj1 and obj2 are both #t or both #f</li>
 * <li>obj1 and obj2 are both symbols and have the same value</li>
 * <li>obj1 and obj2 are both numbers, are numerically equal, and are either
 * both exact or both inexact</li>
 * <li>obj1 and obj2 are both characters and are the same character according to
 * the char=? procedure</li>
 * <li>both obj1 and obj2 are the empty list</li>
 * <li>bj1 and obj2 are pairs, vectors, or strings that denote the same
 * locations in the store</li>
 * <li>obj1 and obj2 are procedures whose location tags are equal</li>
 * </ul>
 * 
 * @author Dominic Charley-Roy
 * 
 */
public class Eqv extends Procedure {

	private static final long serialVersionUID = 8498415355954330802L;

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

		// Class by class special cases
		if (first.getClass() == second.getClass()) {
			if (first instanceof Boolean) {
				return (Boolean) first == (Boolean) second;
			} else if (first instanceof Double) {
				return ((Double) first).doubleValue() == (Double) second;
			} else if (first instanceof Integer) {
				return ((Integer) first).intValue() == (Integer) second;
			} else if (first instanceof Character) {
				return first.equals(second);
			}
		}

		return Boolean.FALSE;
	}

}
