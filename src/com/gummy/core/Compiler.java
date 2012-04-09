package com.gummy.core;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PushbackInputStream;
import java.util.LinkedList;
import java.util.List;

import com.gummy.types.Constant;
import com.gummy.types.Expression;

public class Compiler {

	/**
	 * This reads and analyzes all objects from a stream and writes them to an
	 * Object stream as a {@link LinkedList}. This can then be re-read and
	 * evaluated, skipping the initial reading and analysis phase.
	 * 
	 * @param in
	 *            The stream containing the data to compile
	 * @param out
	 *            The stream to write the compiled object to.
	 * @throws IOException
	 *             If an error occurs while reading or writing
	 */
	public static void compile(PushbackInputStream in, ObjectOutputStream out)
			throws IOException {
		Object expression;
		List<Object> expressions = new LinkedList<Object>();

		// Read and analyze each object
		while ((expression = Reader.read(in)) != Constant.EOF) {
			expressions.add(Expression.analyze(expression));
		}

		// Write the list of expressions to the stream
		out.writeObject(expressions);

	}

}
