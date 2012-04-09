package com.gummy.primitives;

import com.gummy.core.Environment;
import com.gummy.core.Marshall;
import com.gummy.types.Constant;
import com.gummy.types.Expression;
import com.gummy.types.Pair;

/**
 * This primitive defines a variable in a given environment with a specified
 * value.
 * 
 * @author Dominic Charley-Roy
 * 
 */
public class Definition extends Expression {

	private Pair arguments;

	public Definition(Object arguments) {
		this.arguments = Marshall.getPair(arguments);
	}

	@Override
	public Object eval(Environment environment) {
		environment.defineValue(Marshall.getSymbol(arguments.getCar()),
				Expression.eval(Marshall.getPair(arguments.getCdr())
						.getCar(), environment));
		return Constant.UNSPECIFIED;
	}

}
