package com.gummy.core;

import java.io.PrintStream;
import java.io.PushbackInputStream;
import java.util.Map.Entry;

import com.gummy.types.Pair;
import com.gummy.types.Procedure;

public class Writer {

	/**
	 * Converts an object into it's Scheme string representation.
	 * 
	 * @param o
	 *            The object to convert.
	 * @return The string representation of the object.
	 */
	public static String getString(Object o) {
		// Handle all special cases here
		if (o instanceof Boolean) {
			return (o == Boolean.TRUE) ? "#t" : "#f";
		} else if (o instanceof Character) {
			// Look up special cases, or else just return character
			for (Entry<String, Character> entry : Reader.CHARACTER_MAPPING
					.entrySet()) {
				if (o == entry.getValue()) {
					return "#\\" + entry.getKey();
				}
			}

			return "#\\" + o;
		} else if (o instanceof char[] || o instanceof Character[]) {
			return "\"" + new String((char[])o) + "\"";
		} else if (o == Pair.EMPTY_LIST) {
			return "()";
		} else if (o instanceof Procedure) {
			return "<#procedure>";
		} else if (o instanceof PushbackInputStream || o instanceof PrintStream) {
			return "#<input-output-port>";
		}

		return o.toString();
	}

}
