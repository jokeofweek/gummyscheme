package com.gummy.types;

import java.util.ArrayList;
import java.util.List;

import com.gummy.core.Environment;
import com.gummy.core.Writer;

public class Pair extends Expression {

	private static final long serialVersionUID = -4309265489869572010L;

	public static final Pair EMPTY_LIST = new Pair(null, null);

	private Object car, cdr;

	/**
	 * This creates a new Pair with a given car and cdr object.
	 * 
	 * @param car
	 *            The car object.
	 * @param cdr
	 *            The cdr object.
	 */
	public Pair(Object car, Object cdr) {
		this.car = car;
		this.cdr = cdr;
	}

	/**
	 * This expands a {@link Pair} object into a list of {@link Object}s.
	 * 
	 * @param pair
	 *            The {@link Pair} to expand.
	 * @return The {@link List} which is represented by the pair.
	 */
	public static List<Object> expand(Pair pair) {
		List<Object> objects = new ArrayList<Object>();

		// Iterate through until we either reach a pair and not a list,
		// or we reach the empty list.
		while (pair != Pair.EMPTY_LIST) {
			objects.add(pair.car);

			if (pair.cdr instanceof Pair) {
				pair = (Pair) pair.cdr;
			} else {
				objects.add(pair.cdr);
				break;
			}
		}

		return objects;
	}

	/**
	 * This helper method creates a Pair object from a List of objects, and
	 * evaluates each element during the conversion.
	 * 
	 * @param objects
	 *            The list of objects to convert.
	 * @param environment
	 *            The environment to evaluate in.
	 * @return A Pair containing the evaluated elements of the list.
	 */
	public static Pair evaluateFromList(List<Object> objects,
			Environment environment) {
		// Exit early for empty lists
		if (objects.size() == 0)
			return Pair.EMPTY_LIST;

		// Iterate in reverse, evaluating each element and augmenting
		// the pair
		Pair pair = Pair.EMPTY_LIST;

		for (int i = objects.size() - 1; i >= 0; i--) {
			pair = new Pair(Expression.eval(objects.get(i), environment), pair);
		}

		return pair;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		Pair pair = this;

		builder.append('(');

		// Iterate through each element adding the string representation
		// until we reach either the empty list or a cons.
		while (pair != EMPTY_LIST) {
			builder.append(Writer.getString(pair.getCar()));

			if (pair.getCdr() instanceof Pair) {
				pair = (Pair) pair.getCdr();
				if (pair != Pair.EMPTY_LIST) {
					builder.append(' ');
				}
			} else {
				builder.append(" . ");
				builder.append(Writer.getString(pair.getCdr()));
				break;
			}
		}

		builder.append(')');

		return builder.toString();

	}

	public Object getCar() {
		return car;
	}

	public void setCar(Object car) {
		this.car = car;
	}

	public Object getCdr() {
		return cdr;
	}

	public void setCdr(Object cdr) {
		this.cdr = cdr;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gummy.types.Expression#eval(com.gummy.core.Environment)
	 */
	@Override
	public Object eval(Environment environment) {
		if (this == Pair.EMPTY_LIST)
			return Pair.EMPTY_LIST;

		return new Pair(Expression.eval(this.car, environment),
				Expression.eval(this.cdr, environment));
	}

}
