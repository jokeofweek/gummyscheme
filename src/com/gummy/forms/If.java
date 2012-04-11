package com.gummy.forms;

import java.util.List;

import com.gummy.core.Environment;
import com.gummy.core.Marshall;
import com.gummy.types.Constant;
import com.gummy.types.Expression;
import com.gummy.types.Pair;

/**
 * This special form allows conditional statements based on a predicate. It
 * supports two forms:
 * 
 * (if predicate true-expr false-expr) <br/>
 * In this case, the true-expr is evaluated if the predicate evalutes to
 * anything but false, or else the false-expr is evaluated.
 * 
 * (if predicate true-expr) <br/>
 * In this case, the true-expr is evaluated if the predicate evalutes to
 * anything but false, or else the Unspecified constant is returned.
 * 
 * @author Dominic Charley-Roy
 * 
 */
public class If extends Expression {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3712254821553791491L;
	private List<Object> arguments;

	public If(Object arguments) {
		this.arguments = Marshall.getPair(arguments).expand();
	}

	/* (non-Javadoc)
	 * @see com.gummy.types.Expression#eval(com.gummy.core.Environment)
	 */
	@Override
	public Object eval(Environment environment) {
		if (Marshall
				.getPredicate(Expression.eval(arguments.get(0), environment)) == Boolean.TRUE) {
			return Expression.eval(arguments.get(1), environment);
		} else {
			if (arguments.size() > 2) {
				return Expression.eval(arguments.get(2), environment);
			} else {
				return Constant.UNSPECIFIED;
			}
		}

	}

}
