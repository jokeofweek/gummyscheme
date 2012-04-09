package com.gummy.types;

import java.util.List;

import com.gummy.core.Environment;

public abstract class Procedure {

	/**
	 * This invokes invokes a given procedure in an environment with a set of
	 * arguments.
	 * 
	 * @param environment
	 *            The environment to use as scope.
	 * @param arguments
	 *            The arguments apssed to the procedure.
	 */
	public abstract Object apply(Environment environment, List<Object> arguments);

}
