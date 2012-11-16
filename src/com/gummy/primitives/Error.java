package com.gummy.primitives;

import java.util.List;

import com.gummy.core.Environment;
import com.gummy.core.InterpreterException;
import com.gummy.core.Marshall;
import com.gummy.types.Procedure;

/**
 * This procedure allows you to raise an error. Note that this is different
 * than an exception.
 * 
 * @author Dominic Charley-Roy
 */
public class Error extends Procedure {

	private static final long serialVersionUID = -3485070642606999387L;

	@Override
	public Object apply(Environment environment, List<Object> arguments) {
		throw new InterpreterException(
				Marshall.getString(arguments.get(0)));
	}

}
