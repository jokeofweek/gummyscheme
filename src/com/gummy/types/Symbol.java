package com.gummy.types;

import java.io.Serializable;
import java.util.Hashtable;

public class Symbol implements Serializable {

	private static Hashtable<String, Symbol> symbols = new Hashtable<String, Symbol>();

	public String symbol;

	private Symbol(String symbol) {
		this.symbol = symbol;
	}

	public String getSymbol() {
		return this.symbol;
	}

	@Override
	public boolean equals(Object obj) {
		// We can do pointer comparison as there should only ever be one
		// instance of a symbol per string
		return (obj == this);
	}

	@Override
	public int hashCode() {
		return getSymbol().hashCode();
	}

	@Override
	public String toString() {
		return this.symbol;
	}

	public synchronized static Symbol getSymbol(String symbol) {
		Symbol s = symbols.get(symbol);

		if (s == null) {
			s = new Symbol(symbol);
			symbols.put(symbol, s);
		}

		return s;
	}
}
