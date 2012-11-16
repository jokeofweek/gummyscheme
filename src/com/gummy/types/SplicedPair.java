package com.gummy.types;

import java.util.ArrayList;
import java.util.List;

import com.gummy.core.Environment;
import com.gummy.core.Marshall;
import com.gummy.core.Writer;

/**
 * This is a special version of a spliced pair which indicates that when the car
 * and the cdr are evaluted, rather than forming a new pair the evaluated cdr
 * should be cdr'd to the end of the evaluated car.
 * 
 * This allows for unquote splicing.
 * 
 * @author Dominic Charley-Roy
 * 
 */
/**
 * @author Dom
 * 
 */
public class SplicedPair extends Pair {

	private static final long serialVersionUID = -5711601760703649665L;

	public SplicedPair(Object car, Object cdr) {
		super(car, cdr);
	}

	@Override
	public Object eval(Environment environment) {
		Pair p = (Pair) super.eval(environment);


		// A special case has to be made for when the cdr
		// of both the spliced pair and the evaluated pair
		// is an empty list. In that case it just returns
		// the element.
		if ((!(p.getCar() instanceof Pair)) && p.getCdr() == Pair.EMPTY_LIST)
			return p.getCar();
		
		// We make the sure the car is a pair and iterate
		// to the tail.
		Pair car = Marshall.getPair(p.getCar());
		
		while (car.getCdr() != Pair.EMPTY_LIST) {
			car = Marshall.getPair(car.getCdr());
		}

		// Update the cdr
		car.setCdr(p.getCdr());

		// Return the car
		return p.getCar();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return Boolean.FALSE;
		if (!(obj instanceof SplicedPair))
			return Boolean.FALSE;

		return super.equals(obj);
	}

}
