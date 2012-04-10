package com.gummy.types;

import java.io.Serializable;
import java.util.List;

import com.gummy.core.Environment;

public abstract class Procedure implements Serializable {

	private static final long serialVersionUID = -2835581291629986308L;

	/**
	 * This invokes invokes a given procedure in an environment with a set of
	 * arguments.
	 * 
	 * @param environment
	 *            The environment to use as scope.
	 * @param arguments
	 *            The arguments passed to the procedure.
	 */
	public abstract Object apply(Environment environment, List<Object> arguments);

}
