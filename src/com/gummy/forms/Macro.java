package com.gummy.forms;

import com.gummy.core.Environment;
import com.gummy.core.Marshall;
import com.gummy.types.Constant;
import com.gummy.types.Expression;
import com.gummy.types.Pair;
import com.gummy.types.SplicedPair;

/**
 * A Macro expression is similar to a Begin expression, however it must take
 * into consideration rewriting the template and expanding it at eval-time.
 * 
 * @author Dominic Charley-Roy
 * 
 */
public class Macro extends Expression {

	private static final long serialVersionUID = 2996479334019554975L;
	private Pair template;

	public Macro(Pair template) {
		this.template = template;
	}

	private static Object rewriteTemplate(Object template,
			Environment environment) {
		if (template instanceof Pair) {
			if (template == Pair.EMPTY_LIST) {
				return Pair.EMPTY_LIST;
			} else if (template instanceof SplicedPair) {
				return new SplicedPair(rewriteTemplate(
						((Pair) template).getCar(), environment),
						rewriteTemplate(((Pair) template).getCdr(),
								environment));
			} else {
				return new Pair(rewriteTemplate(
						((Pair) template).getCar(), environment),
						rewriteTemplate(((Pair) template).getCdr(),
								environment));
			}
		} else {
			return Expression.eval(Expression.analyze(template),
					environment);
		}
	}

	@Override
	public Object eval(Environment environment) {
		Object retVal = Constant.UNSPECIFIED;

		Pair currentTemplate = this.template;
		while (currentTemplate != Pair.EMPTY_LIST) {
			retVal = Expression.eval(
					Expression.analyze(rewriteTemplate(
							currentTemplate.getCar(), environment)),
					environment);
			currentTemplate = (Pair) currentTemplate.getCdr();
		}

		// We must go through the macro's template, expanding any variables
		// in order to support possible splicing.
		return retVal;
	}

}
