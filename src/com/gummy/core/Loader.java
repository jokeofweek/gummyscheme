package com.gummy.core;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintStream;
import java.io.PushbackInputStream;
import java.util.LinkedList;
import java.util.List;

import com.gummy.types.Constant;
import com.gummy.types.Expression;

public class Loader {

	/**
	 * This repeatedly reads expressions from a stream until the end of the
	 * stream is reached. Each expression is analyzed and evaluated. The result
	 * of each expression is then displayed.
	 * 
	 * @param in
	 *            The stream to read from.
	 * @param out
	 *            The stream to write to.
	 * @param context
	 *            The environment to evaluate in.
	 * @throws IOException
	 *             If an error occurs reading or writing.
	 */
	public static void load(PushbackInputStream in, PrintStream out,
			Environment context) throws IOException {
		Object current;
		String retVal;
		try {
			while (true) {
				current = Reader.read(in);
				if (current == Constant.EOF)
					break;

				retVal = Writer.getString(Expression.eval(
						Expression.analyze(current), context));

				if (!retVal.isEmpty()) {
					out.println(retVal);
				}
			}
		} catch (Exception ex) {
			out.println("Error - " + ex.getClass().getName() + " - "
					+ ex.getMessage());
		}
	}

	/**
	 * This loads a list of expressions from a stream which was compiled using
	 * {@link Compiler#compile(PushbackInputStream, java.io.ObjectOutputStream)}
	 * and evaluates each expression, displaying the result.
	 * 
	 * @param in
	 *            The stream to read from.
	 * @param out
	 *            The stream to write to.
	 * @param context
	 *            The environment to evaluate in.
	 * @throws IOException
	 *             If an error occurs reading or writing.
	 * @throws ClassNotFoundException
	 *             If a compiled object cannot be found.
	 */
	public static void loadCompiled(ObjectInputStream in, PrintStream out,
			Environment context) throws IOException, ClassNotFoundException {
		String retVal;

		// Read expressions from the in stream
		List<Object> expressions = (LinkedList<Object>) in.readObject();

		// Display the return value of each value if it is not empty
		try {
			for (Object expression : expressions) {
				retVal = Writer.getString(Expression.eval(expression, context));
				if (!retVal.isEmpty()) {
					out.println(retVal);
				}
			}
		} catch (Exception ex) {
			out.println("Error - " + ex.getClass().getName() + " - "
					+ ex.getMessage());
		}
	}

}
