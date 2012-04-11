package com.gummy.primitives.io;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.PushbackInputStream;
import java.util.List;

import com.gummy.core.Environment;
import com.gummy.core.InterpreterException;
import com.gummy.core.Marshall;
import com.gummy.types.Expression;
import com.gummy.types.Procedure;

/**
 * This primitive IO procedure allows opening a file for input and returns an
 * input port.
 * 
 * @author Dominic Charley-Roy
 * 
 */
public class OpenInputFile extends Procedure {

	private static final long serialVersionUID = -9202242715689787965L;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gummy.types.Procedure#apply(com.gummy.core.Environment,
	 * java.util.List)
	 */
	@Override
	public Object apply(Environment environment, List<Object> arguments) {
		PushbackInputStream in = null;
		try {
			in = new PushbackInputStream(new FileInputStream(Marshall.getString(Expression.eval(arguments.get(0), environment))));
		} catch (IOException io){
			if (in != null){
				try {
					in.close();
				} catch (IOException e) {}
			}
			throw new InterpreterException("Error opening file - " + io.getMessage());
		}
		return in;
	}

}
