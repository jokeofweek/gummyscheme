package com.gummy.primitives.io;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.PushbackInputStream;
import java.util.List;

import com.gummy.core.Environment;
import com.gummy.core.InterpreterException;
import com.gummy.core.Loader;
import com.gummy.core.Marshall;
import com.gummy.types.Constant;
import com.gummy.types.Procedure;

/**
 * This procedure allows you to load a Scheme file and evaluate it in the
 * environment.
 * 
 * @author Dominic Charley-Roy
 * 
 */
public class Load extends Procedure {

	private static final long serialVersionUID = -822662638363742317L;

	/* (non-Javadoc)
	 * @see com.gummy.types.Procedure#apply(com.gummy.core.Environment, java.util.List)
	 */
	@Override
	public Object apply(Environment environment, List<Object> arguments) {
		PushbackInputStream in = null;

		// Attempt to open the stream.
		try {
			in = new PushbackInputStream(new FileInputStream(
					Marshall.getString(arguments.get(0))));
		} catch (IOException io) {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
				}
			}
			throw new InterpreterException("Could not load file. Error: "
					+ io.getMessage());
		}

		// Load the file
		try {
			Loader.load(in, System.out, environment);
		} catch (IOException e) {
			throw new InterpreterException("Could not read file. Error: "
					+ e.getMessage());
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
				}
			}
		}

		return Constant.UNSPECIFIED;
	}
}
