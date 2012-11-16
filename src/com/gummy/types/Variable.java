package com.gummy.types;

import java.util.Hashtable;

import com.gummy.core.Environment;

public class Variable extends Expression {

	private static final long serialVersionUID = 8708738631680544069L;
	private static Hashtable<Symbol, Variable> variables = new Hashtable<Symbol, Variable>();
	private Symbol symbol;

	/**
	 * This creates a variable reference for a given symbol.
	 * 
	 * @param symbol
	 *            The symbol representing the variable.
	 */
	private Variable(Symbol symbol) {
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

	/**
	 * Fetches a given variable instance for the symbol. Since variables can be
	 * cached, this minimizes memory usage.
	 * 
	 * @param symbol
	 * 		The symbol representing the variable.
	 * @return
	 * 		The variable primitive associated with the symbol.
	 */
	public synchronized static Variable getVariable(Symbol symbol) {
		Variable v = variables.get(symbol);

		if (v == null) {
			v = new Variable(symbol);
			variables.put(symbol, v);
		}

		return v;
	}
}
