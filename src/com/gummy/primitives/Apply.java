package com.gummy.primitives;

import java.util.List;

import com.gummy.core.Environment;
import com.gummy.core.Marshall;
import com.gummy.types.Expression;
import com.gummy.types.Pair;
import com.gummy.types.Procedure;

public class Apply extends Procedure {
	private static final long serialVersionUID = -3991898589031000749L;

	/* (non-Javadoc)
	 * @see com.gummy.types.Procedure#apply(com.gummy.core.Environment, java.util.List)
	 */
	@Override
	public Object apply(Environment environment, List<Object> arguments) {
		Object analyzed = Expression.eval(arguments.get(1), environment);

		return Marshall.getProcedure(
				Expression.eval(arguments.get(0), environment)).apply(
				environment, Marshall.getPair(analyzed).expand());
	}
}
