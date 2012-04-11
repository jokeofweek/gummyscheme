package com.gummy.primitives.io;

import java.util.List;

import com.gummy.core.Environment;
import com.gummy.core.Marshall;
import com.gummy.types.Constant;
import com.gummy.types.Expression;
import com.gummy.types.Procedure;

/**
 * This primitive IO procedure closes an output port.
 * 
 * @author Dominic Charley-Roy
 * 
 */
public class CloseOutputPort extends Procedure {

	private static final long serialVersionUID = -7156983773879734800L;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gummy.types.Procedure#apply(com.gummy.core.Environment,
	 * java.util.List)
	 */
	@Override
	public Object apply(Environment environment, List<Object> arguments) {

		Marshall.getOutputPort(Expression.eval(arguments.get(0), environment)).close();
		return Constant.UNSPECIFIED;
	}

}
