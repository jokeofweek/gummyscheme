package com.gummy.primitives.io;

import java.io.IOException;
import java.util.List;

import com.gummy.core.Environment;
import com.gummy.core.InterpreterException;
import com.gummy.core.Marshall;
import com.gummy.core.Reader;
import com.gummy.types.Expression;
import com.gummy.types.Procedure;

/**
 * This primitive IO procedure reads a Scheme character from an input port.
 * 
 * @author Dominic Charley-Roy
 * 
 */
public class ReadChar extends Procedure {

	private static final long serialVersionUID = -1592191035143349039L;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gummy.types.Procedure#apply(com.gummy.core.Environment,
	 * java.util.List)
	 */
	@Override
	public Object apply(Environment environment, List<Object> arguments) {
		try {
			return Reader.readChar(Marshall.getInputPort(Expression.eval(
					arguments.get(0), environment)));
		} catch (IOException e) {
			throw new InterpreterException("Could not read character - "
					+ e.getMessage());
		}
	}

}
