package com.gummy.types;

import java.io.Serializable;

/**
 * This interface provides a mechanism for analyzing a form contained by a class
 * and is used at the code transformation time (when the read forms are being
 * analyzed).
 * 
 * @author Dominic Charley-Roy
 * 
 */
public interface AnalyzedForm extends Serializable {

	/**
	 * Analyzes the form and returns the result.
	 * 
	 * @return The result of the analysis.
	 */
	public Object analyze();

}
