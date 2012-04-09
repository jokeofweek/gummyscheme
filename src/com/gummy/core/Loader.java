package com.gummy.core;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.PushbackInputStream;

import com.gummy.primitives.Expression;
import com.gummy.types.Constant;

public class Loader {

	public static void load(PushbackInputStream in, PrintWriter out,
			Environment context) throws IOException {
		Object current;
		String retVal;
		
		while (true) {
			current = Reader.read(in);
			if (current == Constant.EOF)
				break;

			retVal = Writer.getString(Expression.eval(
					Expression.analyze(current), context));
			
			if (!retVal.isEmpty())
				System.out.println(retVal);
		}
	}

}
