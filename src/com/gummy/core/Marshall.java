package com.gummy.core;

import com.gummy.types.Procedure;
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

	public static Pair getPair(Object o) {
		if (o instanceof Pair)
			return (Pair) o;
		else
			throw new TypeException(o, Pair.class);
	}

	public static Boolean getPredicate(Object o) {
		if (o != Boolean.FALSE)
			return true;
		else
			return false;
	}

	public static Procedure getProcedure(Object o) {
		if (o instanceof Procedure)
			return (Procedure) o;
		else
			throw new TypeException(o, Procedure.class);
	}

	public static Symbol getSymbol(Object o) {
		if (o instanceof Symbol)
			return (Symbol) o;
		else
			throw new TypeException(o, Symbol.class);
	}

}
