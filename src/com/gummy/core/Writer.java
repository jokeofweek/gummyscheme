package com.gummy.core;

import java.util.Map.Entry;

import com.gummy.types.Pair;

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
			for (Entry<String, Character> entry : Reader.CHARACTER_MAPPING.entrySet()){
				if (o == entry.getValue()){
					return "#\\" + entry.getKey();
				}
			}
			
			return "#\\" + o;
		} else if (o == Pair.EMPTY_LIST) {
			return "()";
		}
		
		return o.toString();
	}

}
