package com.gummy.primitives;

import com.gummy.core.Environment;
import com.gummy.types.Expression;
import com.gummy.types.Symbol;

public class Variable extends Expression {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8708738631680544069L;
	private Symbol symbol;

	/**
	 * This creates a variable reference for a given symbol.
	 * 
	 * @param symbol
	 *            The symbol representing the variable.
	 */
	public Variable(Symbol symbol) {
		this.symbol = symbol;
	}

	/**
	 * @return The symbol for this variable.
	 */
	public Symbol getSymbol() {
		return this.symbol;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gummy.primitives.Expression#eval(com.gummy.core.Environment)
	 */
	@Override
	public Object eval(Environment environment) {
		return environment.getValue(symbol);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		// Return the symbol's string representation
		return this.symbol.getSymbol();
	}
}
