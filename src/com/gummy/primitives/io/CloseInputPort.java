package com.gummy.primitives.io;

import java.io.IOException;
import java.util.List;

import com.gummy.core.Environment;
import com.gummy.core.InterpreterException;
import com.gummy.core.Marshall;
import com.gummy.types.Constant;
import com.gummy.types.Expression;
import com.gummy.types.Procedure;

/**
 * This primitive IO procedure closes an input port.
 * 
 * @author Dominic Charley-Roy
 * 
 */
public class CloseInputPort extends Procedure {

	private static final long serialVersionUID = 8069317286937190778L;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gummy.types.Procedure#apply(com.gummy.core.Environment,
	 * java.util.List)
	 */
	@Override
	public Object apply(Environment environment, List<Object> arguments) {

		try {
			Marshall.getInputPort(Expression.eval(arguments.get(0), environment)).close();
		} catch (IOException io) {
			throw new InterpreterException("Error closing port - "
					+ io.getMessage());
		}
		return Constant.UNSPECIFIED;
	}

}
