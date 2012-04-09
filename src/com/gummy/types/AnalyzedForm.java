package com.gummy.types;

/**
 * This interface provides a mechanism for analyzing a form contained by a class
 * and is used at the code transformation time (when the read forms are being
 * analyzed).
 * 
 * @author Dominic Charley-Roy
 * 
 */
public interface AnalyzedForm {

	/**
	 * Analyzes the form and returns the result.
	 * 
	 * @return The result of the analysis.
	 */
	public Object analyze();

}
