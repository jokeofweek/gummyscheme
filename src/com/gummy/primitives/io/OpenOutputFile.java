package com.gummy.primitives.io;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;

import com.gummy.core.Environment;
import com.gummy.core.InterpreterException;
import com.gummy.core.Marshall;
import com.gummy.types.Expression;
import com.gummy.types.Procedure;

/**
 * This primitive IO procedure allows opening a file for output and returns an
 * output port.
 * 
 * @author Dominic Charley-Roy
 * 
 */
public class OpenOutputFile extends Procedure {

	private static final long serialVersionUID = 4086750532289505516L;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gummy.types.Procedure#apply(com.gummy.core.Environment,
	 * java.util.List)
	 */
	@Override
	public Object apply(Environment environment, List<Object> arguments) {
		PrintStream out = null;
		try {
			out = new PrintStream(new FileOutputStream(Marshall.getString(Expression.eval(arguments.get(0), environment))));
		} catch (IOException io){
			if (out != null){
				out.close();
			}
			throw new InterpreterException("Error opening file - " + io.getMessage());
		}
		return out;
	}

}
