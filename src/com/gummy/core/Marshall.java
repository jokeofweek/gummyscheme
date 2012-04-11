package com.gummy.core;

import java.io.PrintStream;
import java.io.PushbackInputStream;

import com.gummy.types.AnalyzedForm;
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

	public static char[] getSchemeString(Object o) {
		if (o instanceof char[])
			return (char[]) o;
		else
			throw new TypeException(o, String.class);
	}

	public static String getString(Object o) {
		if (o instanceof char[])
			return new String(((char[]) o));
		else if (o instanceof String)
			return (String) o;
		else
			throw new TypeException(o, String.class);
	}

	public static Symbol getSymbol(Object o) {
		if (o instanceof Symbol)
			return (Symbol) o;
		else
			throw new TypeException(o, Symbol.class);
	}
	
	public static AnalyzedForm getAnalyzedForm(Object o){
		if (o instanceof AnalyzedForm)
			return (AnalyzedForm) o;
		else
			throw new TypeException(o, AnalyzedForm.class);
	}

	public static PushbackInputStream getInputPort(Object o){
		if (o instanceof PushbackInputStream)
			return (PushbackInputStream) o;
		else
			throw new TypeException(o, PushbackInputStream.class);
	}
	
	public static PrintStream getOutputPort(Object o){
		if (o instanceof PrintStream)
			return (PrintStream) o;
		else
			throw new TypeException(o, PrintStream.class);
	}
}
