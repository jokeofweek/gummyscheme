package com.gummy.core;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.PushbackInputStream;

import com.gummy.types.Constant;
import com.gummy.types.Expression;

public class Loader {

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

				if (!retVal.isEmpty())
					out.println(retVal);
			}
		} catch (Exception ex) {
			out.println("Error - " + ex.getClass().getName() + " - "
					+ ex.getMessage());
		}
	}

}
