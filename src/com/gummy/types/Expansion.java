package com.gummy.types;

import java.io.Serializable;

public abstract class Expansion implements Serializable {

	private static final long serialVersionUID = -402258118270700137L;

	/**
	 * This expands a set of expressions into their final form.
	 * @param expressions
	 * 		The set of expressions to expand.
	 * @return
	 * 		The expansion result.
	 */
	public abstract Object expand(Pair expressions);
	
}
