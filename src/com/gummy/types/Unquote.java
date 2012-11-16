package com.gummy.types;

/**
 * This evaluation form is meant to be used within a {@link Quasiquote} form and
 * allows interpolation of evaluated values in a quoted value.
 * 
 * Note that the expression can be spliced into the list rather than just unquoted,
 * giving behavior equivalent to the unquote-splicing function.
 * 
 * For example, when splicing is true:
 * `(1 ,@(list 2 3 4 5) 6) => '(1 2 3 4 5 6)
 * 
 * When splicing is not true:
 * `(1 ,(list 2 3 4 5) 6) => '(1 (2 3 4 5) 6)
 * @author Dominic Charley-Roy
 * 
 */
public class Unquote implements AnalyzedForm {

	private static final long serialVersionUID = 7323057766963994663L;
	private Object expression;
	private boolean splicing;

	public Unquote(Object expression, boolean splicing) {
		this.expression = expression;
		this.splicing = splicing;
	}
	
	public boolean isSplicing() {
		return this.splicing;
	}

	@Override
	public Object analyze() {
		return Expression.analyze(expression);
	}

}
