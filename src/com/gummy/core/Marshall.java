package com.gummy.core;

import com.gummy.types.Symbol;
import com.gummy.types.Pair;

/**
 * This class provides type marshalling functions in order to ensure safe
 * variable types.
 * 
 * @author Dominic Charley-Roy
 * 
 */
public class Marshall {

	public static Boolean getPredicate(Object o) {
		if (o != Boolean.FALSE)
			return true;
		else
			return false;
	}

	public static Pair getPair(Object o) {
		if (o instanceof Pair)
			return (Pair) o;
		else
			throw new TypeException(o, Pair.class);
	}

	public static Symbol getSymbol(Object o) {
		if (o instanceof Symbol)
			return (Symbol) o;
		else
			throw new TypeException(o, Symbol.class);
	}

}
