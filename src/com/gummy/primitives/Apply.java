package com.gummy.primitives;

import java.util.List;

import com.gummy.core.Environment;
import com.gummy.core.Marshall;
import com.gummy.types.Expression;
import com.gummy.types.Pair;
import com.gummy.types.Procedure;

public class Apply extends Procedure {
	@Override
	public Object apply(Environment environment, List<Object> arguments) {
		Object analyzed = Expression.eval(arguments.get(1), environment);

		return Marshall.getProcedure(
				Expression.eval(arguments.get(0), environment)).apply(
				environment, Pair.expand(Marshall.getPair(analyzed)));
	}
}
