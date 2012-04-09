package com.gummy.core;

import java.io.IOException;
import java.io.PushbackInputStream;
import java.util.Map.Entry;
import java.util.Hashtable;
import java.util.Map;

import com.gummy.core.InterpreterException;
import com.gummy.types.Constant;
import com.gummy.types.Pair;
import com.gummy.types.Quasiquote;
import com.gummy.types.Quote;
import com.gummy.types.Symbol;
import com.gummy.types.Unquote;

public class Reader {

	/**
	 * This represents a mapping of strings to character entities used for
	 * parsing character tokens.
	 */
	public static final Hashtable<String, Character> CHARACTER_MAPPING = new Hashtable<String, Character>();

	static {
		// Setup the character mappings
		CHARACTER_MAPPING.put("space", ' ');
		CHARACTER_MAPPING.put("tab", '\t');
		CHARACTER_MAPPING.put("newline", '\n');
	}

	/**
	 * Reads an expression from an input stream.
	 * 
	 * @param in
	 *            The stream to read from.
	 * @return The expression read from the stream.
	 * @throws IOException
	 *             If an error occurs while reading.
	 */
	public static Object read(PushbackInputStream in) throws IOException {
		return read(in, ReaderState.NORMAL);
	}

	/**
	 * Reads an expression from an input stream using a given
	 * {@link ReaderState} for processing rules.
	 * 
	 * @param in
	 *            The stream to read from.
	 * @param state
	 *            The state to use when reading.
	 * @return The expression read from the stream.
	 * @throws IOException
	 *             If an error occurs while reading.
	 */
	public static Object read(PushbackInputStream in, ReaderState state)
			throws IOException {
		if (!skipWhitespace(in))
			return Constant.EOF;

		// Determine what we are reading based on the first character.
		char start = (char) in.read();

		if (start == '(') {
			return readPair(in, state);
		} else if (start == '"') {
			return readString(in);
		} else if ((int) start == -1) {
			return Constant.EOF;
		} else if (start == '\'') {
			return new Quote(read(in, ReaderState.QUOTE));
		} else if (start == '`') {
			return new Quasiquote(read(in, ReaderState.QUASIQUOTE));
		} else if (start == ',') {
			if (state != ReaderState.QUASIQUOTE) {
				throw new InterpreterException(
						"Cannot unquote value while not in quasiquote.");
			}
			return new Unquote(read(in, ReaderState.NORMAL));
		} else {
			// Unread the character and read a token.
			in.unread((int) start);
			return parseToken(readToken(in));
		}
	}

	/**
	 * This reads a pair from the current stream (either a list or dotted pair).
	 * 
	 * @param in
	 *            The stream to read from.
	 * @param state
	 * 			  The current state of the reader.
	 * @return The {@link Pair} object representing the pair read from the
	 *         stream.
	 * @throws IOException
	 *             If an error occurs reading from the stream.
	 * @throws InterpreterException
	 *             If the pair is unbalanced.
	 */
	private static Object readPair(PushbackInputStream in, ReaderState state) throws IOException {
		// Eat all whitespace
		skipWhitespace(in);

		// Check if the first character is a closing parenthesis and if so
		// return the empty list.
		int first;
		first = in.read();

		if (first == -1)
			throw new InterpreterException("Unbalanced parenthesis.");

		if (((char) first) == ')')
			return Pair.EMPTY_LIST;

		// If the character is a dot, it is a dotted pair and not a list.
		if (((char) first) == '.') {
			Object cdr = read(in, state);

			// Next token should be a closing parenthesis, we must eat it
			skipWhitespace(in);
			if ((char) in.read() != ')')
				throw new InterpreterException("Incomplete pair form.");
			return cdr;
		}

		// Unread the token and build the pair
		in.unread(first);
		return new Pair(read(in, state), readPair(in, state));
	}

	/**
	 * Reads a string from a stream. This assumes the opening quote was already
	 * read.
	 * 
	 * @param in
	 *            The stream to read from.
	 * @return The character array representing the string.
	 * @throws IOException
	 *             If an error occurs while reading.
	 * @throws InterpreterException
	 *             If the string is unterminated.
	 */
	private static char[] readString(PushbackInputStream in) throws IOException {
		StringBuilder builder = new StringBuilder();
		char c;

		while ((c = (char) in.read()) != '"') {
			if (c == -1)
				throw new InterpreterException("Unterminated string.");
			builder.append(c);
		}

		return builder.toString().toCharArray();
	}

	/**
	 * Reads a token from a stream. This token is not parsed and should be
	 * parsed via {@link Reader#parseToken(String)}. This assumes the first
	 * character of the token was not read.
	 * 
	 * @param in
	 *            The stream to read from.
	 * @return The string representation of the token which was read.
	 * @throws IOException
	 *             If an error occurs while reading.
	 */
	private static String readToken(PushbackInputStream in) throws IOException {

		StringBuilder builder = new StringBuilder();
		int c;

		while ((c = in.read()) != -1) {
			// When we hit an end of token character, unread it and exit
			if (isEndOfTokenChar((char) c)) {
				in.unread(c);
				break;
			}
			builder.append((char) c);
		}

		return builder.toString();
	}

	/**
	 * This parses a token and returns the value it is representing, such as a
	 * boolean, a number or a symbol.
	 * 
	 * @param token
	 *            The string representation of the token.
	 * @return The parsed token.
	 * @throws InterpreterException
	 *             This is thrown if a token is not valid.
	 */
	private static Object parseToken(String token) {
		char first = token.charAt(0);

		// If the character is a boolean or a character, attempt to parse it.
		if (first == '#') {
			if ("#t".equals(token))
				return Boolean.TRUE;
			if ("#f".equals(token))
				return Boolean.FALSE;
			if (token.length() > 2) {
				if (token.charAt(1) == '\\') {
					return parseCharacter(token);
				}
			}

			throw new InterpreterException("Invalid token " + token);
		}

		// Only parse the token if it has the possibility of being a numerical
		// value.
		if (first == '-' || first == '+' || first == '.'
				|| (first >= '0' && first <= '9')) {
			try {
				return new Integer(token);
			} catch (NumberFormatException nef) {
				try {
					return new Double(token);
				} catch (NumberFormatException nef2) {
				}
			}
		}

		// Finally, it must be a symbol.
		return Symbol.getSymbol(token);

	}

	/**
	 * This converts a character token to the appropriate character object.
	 * 
	 * @param token
	 *            The character token (including the #\).
	 * @return The proper character object.
	 * @throws InterpreterException
	 *             If the character token is not valid.
	 */
	public static Character parseCharacter(String token) {
		// If the token is a 1 char character, simply convert it.
		if (token.length() == 3) {
			return token.charAt(2);
		}

		// Try to parse it out of a word
		token = token.substring(2);
		for (Entry<String, Character> e : CHARACTER_MAPPING.entrySet()) {
			if (e.getKey().equals(token))
				return e.getValue();
		}

		throw new InterpreterException("Invalid character: #\\" + token);
	}

	/**
	 * Checks whether a given character is considered the end of a token.
	 * 
	 * @param c
	 *            The character to check.
	 * @return True if the character is the end of a token or not.
	 */
	private static boolean isEndOfTokenChar(char c) {
		return (c == ' ' || c == '\r' || c == '\n' || c == '\t' || c == ';'
				|| c == ')' || c == '(');
	}

	/**
	 * This skips all whitespace from a stream (assuming we are not currently in
	 * a string).
	 * 
	 * @param in
	 *            The stream to advance.
	 * @return True if the stream has advanced to a character, or false if we
	 *         have reached end of stream.
	 * @throws IOException
	 *             If an error occurs while reading from the stream.
	 */
	private static boolean skipWhitespace(PushbackInputStream in)
			throws IOException {
		int c;
		char curChar;

		while ((c = in.read()) != -1) {
			curChar = (char) c;

			// If character is not a whitespace character, unread and exit
			if (curChar != ' ' && curChar != '\n' && curChar != '\r'
					&& curChar != '\t') {
				in.unread(c);
				return true;
			}
		}

		return false;
	}

}
